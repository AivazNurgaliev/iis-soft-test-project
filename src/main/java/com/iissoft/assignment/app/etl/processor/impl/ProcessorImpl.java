package com.iissoft.assignment.app.etl.processor.impl;

import com.iissoft.assignment.app.etl.processor.Processor;
import com.iissoft.assignment.app.model.EmployeeDto;
import com.iissoft.assignment.app.model.NaturalKey;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProcessorImpl implements Processor {

    @Override
    public Map<NaturalKey, EmployeeDto> process(List<EmployeeDto> employees) {
        Map<NaturalKey, EmployeeDto> res = new HashMap<>();

        for (EmployeeDto employee : employees) {
            NaturalKey naturalKey = new NaturalKey(employee.getDepCode(), employee.getDepJob());
            System.out.println(naturalKey.hashCode());
            if(res.containsKey(naturalKey)) {
                throw new RuntimeException("Duplicate key: " + naturalKey + " in xml");
            }

            res.put(naturalKey, employee);
        }

        return res;
    }
}
