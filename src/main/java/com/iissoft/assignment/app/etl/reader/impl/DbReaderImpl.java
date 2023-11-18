package com.iissoft.assignment.app.etl.reader.impl;

import com.iissoft.assignment.app.config.datasource.PostgresConfigProperties;
import com.iissoft.assignment.app.etl.reader.DbReader;
import com.iissoft.assignment.app.exception.EtlReaderException;
import com.iissoft.assignment.app.model.Employee;
import com.iissoft.assignment.app.model.EmployeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Имплементация интерфейса DbReader, документация находится в интерфейсе
 */
@Component
public class DbReaderImpl implements DbReader {
    private static final String SELECT_ALL_EMPLOYEES = "select * from employees";
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private final PostgresConfigProperties postgresConfigProperties;
    private static final Logger logger = LoggerFactory.getLogger(DbReaderImpl.class);

    public DbReaderImpl(PostgresConfigProperties postgresConfigProperties) {
        this.postgresConfigProperties = postgresConfigProperties;
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
            throw new EtlReaderException("Couldn't open the connection to DB", e);
        }

        logger.info("Successfully opened the connection to DB");
    }

    @Override
    public List<EmployeeDto> read() {
        logger.info("reading from db...");
        open();
        List<EmployeeDto> employees = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EmployeeDto item = new EmployeeDto();
                item.setDepCode(resultSet.getString("DepCode"));
                item.setDepJob(resultSet.getString("DepJob"));
                item.setDescription(resultSet.getString("Description"));
                employees.add(item);
            }
        } catch (SQLException e) {
            logger.error("Failed to read from the result set", e.getMessage());
            throw new EtlReaderException("Failed to read from the result set", e);
        } finally {
            close();
        }
        logger.info("Successfully read from db");
        return employees;
    }

    @Override
    public void close() {
        logger.info("Closing the connection to DB...");
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Failed to close the JDBC connection and resources", e.getMessage());
            throw new EtlReaderException("Failed to close the JDBC connection and resources", e);
        }
        logger.info("Successfully closed the connection to DB");
    }

}
