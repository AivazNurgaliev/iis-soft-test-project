package com.iissoft.assignment.app.etl.processor.impl;

import com.iissoft.assignment.app.etl.processor.Processor;
import com.iissoft.assignment.app.exception.KeyDuplicationException;
import com.iissoft.assignment.app.model.EmployeeDto;
import com.iissoft.assignment.app.model.NaturalKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Имплементация интерфейса Processor, документация находится в интерфейсе
 */
@Component
public class ProcessorImpl implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(ProcessorImpl.class);
    @Override
    public Map<NaturalKey, EmployeeDto> process(List<EmployeeDto> employees) {
        logger.info("Starting processing info from xml");
        Map<NaturalKey, EmployeeDto> res = new HashMap<>();

        for (EmployeeDto employee : employees) {
            NaturalKey naturalKey = new NaturalKey(employee.getDepCode(), employee.getDepJob());
            if(res.containsKey(naturalKey)) {
                logger.error("Duplicate key: " + naturalKey + " in xml");
                throw new KeyDuplicationException("Duplicate key: " + naturalKey.getDepCode()
                        + " " + naturalKey.getDepJob() + " in xml");
            }

            res.put(naturalKey, employee);
        }
        logger.info("Successfully processed info from xml");

        return res;
    }
}
