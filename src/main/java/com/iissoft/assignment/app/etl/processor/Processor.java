package com.iissoft.assignment.app.etl.processor;

import com.iissoft.assignment.app.model.EmployeeDto;
import com.iissoft.assignment.app.model.NaturalKey;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс для преобразования данных EmployeeDto в Map с ключами типа NaturalKey и значениями типа EmployeeDto
 * нужно для контроля над натуральными ключами(синхронизация)
 */
public interface Processor {
    /**
     * Обрабатывает список объектов EmployeeDto и возвращает отображение Map с ключами типа NaturalKey
     * и значениями типа EmployeeDto.
     *
     * @param employees  список объектов EmployeeDto для обработки
     * @return           отображение Map с ключами типа NaturalKey и значениями типа EmployeeDto
     */
    Map<NaturalKey, EmployeeDto> process(List<EmployeeDto> employees);
}
