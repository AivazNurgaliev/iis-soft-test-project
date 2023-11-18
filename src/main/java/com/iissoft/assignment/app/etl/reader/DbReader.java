package com.iissoft.assignment.app.etl.reader;

import com.iissoft.assignment.app.model.Employee;
import com.iissoft.assignment.app.model.EmployeeDto;

import java.util.List;

public interface DbReader {
    List<EmployeeDto> read();
    void open();
    void close();
}
