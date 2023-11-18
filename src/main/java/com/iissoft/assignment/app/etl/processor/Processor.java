package com.iissoft.assignment.app.etl.processor;

import com.iissoft.assignment.app.model.EmployeeDto;
import com.iissoft.assignment.app.model.NaturalKey;

import java.util.List;
import java.util.Map;

public interface Processor {
    Map<NaturalKey, EmployeeDto> process(List<EmployeeDto> employees);
}
