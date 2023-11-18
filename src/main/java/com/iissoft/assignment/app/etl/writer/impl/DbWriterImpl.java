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
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private final PostgresConfigProperties postgresConfigProperties;

    @Autowired
    public DbWriterImpl(PostgresConfigProperties postgresConfigProperties) {
        this.postgresConfigProperties = postgresConfigProperties;
    }

    // TODO: 18.11.2023 COMMIT AND ROLLBACK 
    @Override
    public void write(Map<NaturalKey, EmployeeDto> map, List<EmployeeDto> employeeDtos) {
        open();

        for (EmployeeDto employee : employeeDtos) {
            NaturalKey naturalKey = new NaturalKey(employee.getDepCode(), employee.getDepJob());

            if(!map.containsKey(naturalKey)) {
                //удаление
                try {
                    preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_BY_NATURAL_KEY);
                    preparedStatement.setString(1, employee.getDepCode());
                    preparedStatement.setString(2, employee.getDepJob());

                    // FIXME: 18.11.2023 todo TEST
                    int rowsDeleted = preparedStatement.executeUpdate();
                    if(rowsDeleted > 0) {
                        System.out.println("Deleted rows: " + rowsDeleted);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if(map.containsKey(naturalKey) && !map.get(naturalKey).equals(employee)) {
/*                System.out.println(employee.getDepCode());
                System.out.println(employee.getDepJob());
                System.out.println(employee.getDescription());
                System.out.println(map.get(naturalKey).getDescription());
                //обновление*/
                try {
                    preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE_BY_NATURAL_KEY);
                    preparedStatement.setString(1, map.get(naturalKey).getDescription());
                    preparedStatement.setString(2, employee.getDepCode());
                    preparedStatement.setString(3, employee.getDepJob());

                    // FIXME: 18.11.2023 todo TEST
                    int rowsUpdated = preparedStatement.executeUpdate();
                    if(rowsUpdated > 0) {
                        System.out.println("Updated rows: " + rowsUpdated);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
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
