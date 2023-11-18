package com.iissoft.assignment.app.etl.writer;

import com.iissoft.assignment.app.model.EmployeeDto;
import com.iissoft.assignment.app.model.NaturalKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Интерфейс для записи, удаления, обновления данных в базу
 */
public interface DbWriter {
    /**
     * Writes the given XML map and list of employee DTOs to a file.
     *
     * @param  map            Мапа, чтобы вести контроль над ествественными ключами
     * @param  employeeDtos   Лист дто работников, которые есть в базе
     */
    void write(Map<NaturalKey, EmployeeDto> map, List<EmployeeDto> employeeDtos);

    /**
     * Открывает JDBC cоединение
     */
    void open();

    /**
     * Закрывает все ресурсы с БД
     */
    void close();
}
