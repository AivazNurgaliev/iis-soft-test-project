package com.iissoft.assignment.app.etl.reader.impl;

import com.iissoft.assignment.app.config.datasource.PostgresConfigProperties;
import com.iissoft.assignment.app.etl.reader.DbReader;
import com.iissoft.assignment.app.model.Employee;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbReaderImpl implements DbReader {
    private static final String SELECT_ALL_EMPLOYEES = "select * from employees";
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private final PostgresConfigProperties postgresConfigProperties;

    public DbReaderImpl(PostgresConfigProperties postgresConfigProperties) {
        this.postgresConfigProperties = postgresConfigProperties;
    }

    @Override
    public void open() {
        try {
            Class.forName(postgresConfigProperties.getDriverClassName());
            connection = DriverManager.getConnection(postgresConfigProperties.getUrl(),
                    postgresConfigProperties.getUsername(), postgresConfigProperties.getPassword());
            preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);

            resultSet = preparedStatement.executeQuery();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't open the connection to DB", e);
        }
    }

    @Override
    public List<Employee> read() {
        open();
        List<Employee> employees = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Employee item = new Employee();
                item.setId(resultSet.getInt("ID"));
                item.setDepCode(resultSet.getString("DepCode"));
                item.setDepJob(resultSet.getString("DepJob"));
                item.setDescription(resultSet.getString("Description"));
                employees.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read the next item from the result set", e);
        } finally {
            close();
        }

        return employees;
    }

    @Override
    public void close() {
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
            e.printStackTrace();
            throw new RuntimeException("Failed to close the JDBC connection and resources", e);
        }
    }

}
