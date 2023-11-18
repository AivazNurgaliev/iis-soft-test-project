package com.iissoft.assignment.app.etl.writer.impl;

import com.iissoft.assignment.app.config.datasource.PostgresConfigProperties;
import com.iissoft.assignment.app.etl.writer.DbWriter;
import com.iissoft.assignment.app.model.EmployeeDto;
import com.iissoft.assignment.app.model.NaturalKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Map;

@Component
public class DbWriterImpl implements DbWriter {
    private static final String DELETE_EMPLOYEE_BY_NATURAL_KEY = "delete from employees where DepCode = ? and DepJob = ?";
    private static final String UPDATE_EMPLOYEE_BY_NATURAL_KEY = "update employees set Description = ? where DepCode = ? and DepJob = ?";
    private static final String INSERT_EMPLOYEE = "insert into employees (DepCode, DepJob, Description) values (?, ?, ?)";
    private Connection connection;
    private PreparedStatement preparedStatement;
    private final PostgresConfigProperties postgresConfigProperties;

    @Autowired
    public DbWriterImpl(PostgresConfigProperties postgresConfigProperties) {
        this.postgresConfigProperties = postgresConfigProperties;
    }

    // TODO: 18.11.2023 COMMIT AND ROLLBACK + CLOSE ALL RESOURCES
    @Override
    public void write(Map<NaturalKey, EmployeeDto> map, List<EmployeeDto> employeeDtos) {
        open();

        try {
            connection.setAutoCommit(false);
            for (EmployeeDto employee : employeeDtos) {
                NaturalKey naturalKey = new NaturalKey(employee.getDepCode(), employee.getDepJob());

                if (!map.containsKey(naturalKey)) {
                    preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_BY_NATURAL_KEY);
                    preparedStatement.setString(1, employee.getDepCode());
                    preparedStatement.setString(2, employee.getDepJob());

                    // FIXME: 18.11.2023 todo TEST + LOGGING
                    int rowsDeleted = preparedStatement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("Deleted rows: " + rowsDeleted);
                    }
                } else if (map.containsKey(naturalKey) && !map.get(naturalKey).equals(employee)) {
                    preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE_BY_NATURAL_KEY);
                    preparedStatement.setString(1, map.get(naturalKey).getDescription());
                    preparedStatement.setString(2, employee.getDepCode());
                    preparedStatement.setString(3, employee.getDepJob());

                    // FIXME: 18.11.2023 todo TEST
                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Updated rows: " + rowsUpdated);
                    }

                    map.remove(naturalKey);
                } else if (map.containsKey(naturalKey) && map.get(naturalKey).equals(employee)) {
                    map.remove(naturalKey);
                }
            }

            if (map.size() > 0) {
                for (Map.Entry<NaturalKey, EmployeeDto> entry : map.entrySet()) {
                    preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE);
                    preparedStatement.setString(1, entry.getKey().getDepCode());
                    preparedStatement.setString(2, entry.getKey().getDepJob());
                    preparedStatement.setString(3, entry.getValue().getDescription());

                    // FIXME: 18.11.2023 todo TEST
                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Inserted rows: " + rowsInserted);
                    }
                }
            }

            connection.commit();
        } catch (RuntimeException | SQLException e) {
            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("cannot rollback");
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Failed to write the next item to the result set", e);
        } finally {
            close();
        }
    }

    @Override
    public void open() {
        try {
            Class.forName(postgresConfigProperties.getDriverClassName());
            connection = DriverManager.getConnection(postgresConfigProperties.getUrl(),
                    postgresConfigProperties.getUsername(), postgresConfigProperties.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't open the connection to DB", e);
        }
    }

    @Override
    public void close() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to close the JDBC connection and resources", e);
        }
    }
}
