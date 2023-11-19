package com.iissoft.assignment.app.etl.writer.impl;

import com.iissoft.assignment.app.config.datasource.PostgresConfigProperties;
import com.iissoft.assignment.app.etl.writer.DbWriter;
import com.iissoft.assignment.app.exception.EtlWriterException;
import com.iissoft.assignment.app.model.EmployeeDto;
import com.iissoft.assignment.app.model.NaturalKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Имплементация интерфейса DbWriter, документация находится в интерфейсе
 */
@Component
public class DbWriterImpl implements DbWriter {
    private static final String DELETE_EMPLOYEE_BY_NATURAL_KEY = "delete from employees where DepCode = ? and DepJob = ?";
    private static final String UPDATE_EMPLOYEE_BY_NATURAL_KEY = "update employees set Description = ? where DepCode = ? and DepJob = ?";
    private static final String INSERT_EMPLOYEE = "insert into employees (DepCode, DepJob, Description) values (?, ?, ?)";
    private Connection connection;
    private PreparedStatement preparedStatement;
    private final PostgresConfigProperties postgresConfigProperties;
    private static final Logger logger = LoggerFactory.getLogger(DbWriterImpl.class);

    @Autowired
    public DbWriterImpl(PostgresConfigProperties postgresConfigProperties) {
        this.postgresConfigProperties = postgresConfigProperties;
    }

    @Override
    public void write(Map<NaturalKey, EmployeeDto> map, List<EmployeeDto> employeeDtos) {
        // TODO: 18.11.2023 в ифах - сделать разные методы аддДБ,  updateDb
        open();
        logger.info("Writing to db...");
        try {
            connection.setAutoCommit(false);
            for (EmployeeDto employee : employeeDtos) {
                NaturalKey naturalKey = new NaturalKey(employee.getDepCode(), employee.getDepJob());

                if (!map.containsKey(naturalKey)) {
                    deleteEmployee(employee);
                } else if (map.containsKey(naturalKey) && !map.get(naturalKey).equals(employee)) {
                    updateEmployee(map, employee, naturalKey);

                    map.remove(naturalKey);
                } else if (map.containsKey(naturalKey) && map.get(naturalKey).equals(employee)) {
                    map.remove(naturalKey);
                }
            }

            if (map.size() > 0) {
                for (Map.Entry<NaturalKey, EmployeeDto> entry : map.entrySet()) {
                    insertEmployee(entry);
                }
            }

            connection.commit();
            logger.info("Successfully wrote to db and committed transaction");
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    logger.info("Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException ex) {
                    logger.error("Failed to rollback transaction", ex.getMessage());
                    throw new EtlWriterException("Cannot rollback");
                }
            }
            logger.info("Failed to write to db", e.getMessage());
            throw new EtlWriterException("Failed to write to db", e);
        } finally {
            close();
        }
    }


    @Override
    public void open() {
        logger.info("Opening the connection to DB");
        try {
            Class.forName(postgresConfigProperties.getDriverClassName());
            connection = DriverManager.getConnection(postgresConfigProperties.getUrl(),
                    postgresConfigProperties.getUsername(), postgresConfigProperties.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Couldn't open the connection to DB", e.getMessage());
            throw new EtlWriterException("Couldn't open the connection to DB", e);
        }

        logger.info("Successfully opened the connection to DB");
    }

    @Override
    public void close() {
        logger.info("Closing the connection to DB...");
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Failed to close the JDBC connection and resources", e.getMessage());
            throw new EtlWriterException("Failed to close the JDBC connection and resources", e);
        }
        logger.info("Successfully closed the connection to DB");
    }

    private void insertEmployee(Map.Entry<NaturalKey, EmployeeDto> entry) throws SQLException {
        preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE);
        preparedStatement.setString(1, entry.getKey().getDepCode());
        preparedStatement.setString(2, entry.getKey().getDepJob());
        preparedStatement.setString(3, entry.getValue().getDescription());

        preparedStatement.executeUpdate();
    }

    private void updateEmployee(Map<NaturalKey, EmployeeDto> map, EmployeeDto employee,
                                NaturalKey naturalKey) throws SQLException {
        preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE_BY_NATURAL_KEY);
        preparedStatement.setString(1, map.get(naturalKey).getDescription());
        preparedStatement.setString(2, employee.getDepCode());
        preparedStatement.setString(3, employee.getDepJob());

        preparedStatement.executeUpdate();
    }

    private void deleteEmployee(EmployeeDto employee) throws SQLException {
        preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_BY_NATURAL_KEY);
        preparedStatement.setString(1, employee.getDepCode());
        preparedStatement.setString(2, employee.getDepJob());

        preparedStatement.executeUpdate();
    }

}
