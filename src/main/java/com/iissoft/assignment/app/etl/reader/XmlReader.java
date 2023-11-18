package com.iissoft.assignment.app.etl.reader;

import com.iissoft.assignment.app.model.Employee;
import com.iissoft.assignment.app.model.EmployeeDto;

import java.io.File;
import java.util.List;

/**
 * Интерфейс для чтения данных из XML
 */
public interface XmlReader {
    /**
     * Читает данные из указанного файла и возвращает список объектов EmployeeDto.
     *
     * @param file  файл, из которого необходимо прочитать данные
     * @return      список объектов EmployeeDto
     */
    List<EmployeeDto> read(File file);
}
