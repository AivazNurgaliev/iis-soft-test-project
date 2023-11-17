package com.iissoft.assignment.app.etl.reader;

import com.iissoft.assignment.app.model.Employee;

import java.util.List;

public interface DbReader {
    List<Employee> read();
    void open();
    void close();
}
