package com.iissoft.assignment.app.etl.reader;

import com.iissoft.assignment.app.model.Employee;
import com.iissoft.assignment.app.model.EmployeeDto;

import java.io.File;
import java.util.List;

public interface XmlReader {
    List<EmployeeDto> read(File file);
}
