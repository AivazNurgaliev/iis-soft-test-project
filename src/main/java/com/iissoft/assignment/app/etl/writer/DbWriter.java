package com.iissoft.assignment.app.etl.writer;

import com.iissoft.assignment.app.model.EmployeeDto;
import com.iissoft.assignment.app.model.NaturalKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DbWriter {
    void write(Map<NaturalKey, EmployeeDto> xml, List<EmployeeDto> employeeDtos);
    void open();
    void close();
}
