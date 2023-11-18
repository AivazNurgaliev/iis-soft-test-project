package com.iissoft.assignment.app.etl.reader;

import com.iissoft.assignment.app.model.Employee;
import com.iissoft.assignment.app.model.EmployeeDto;

import java.util.List;

/**
 * Интерфейс для чтения из БД
 */
public interface DbReader {
    /**
     * Читает данные и возвращает список объектов EmployeeDto.
     *
     * @return  список объектов EmployeeDto
     */
    List<EmployeeDto> read();
    /**
     * Открывает JDBC cоединение
     */
    void open();
    /**
     * Закрывает все ресурсы с БД
     */
    void close();
}
