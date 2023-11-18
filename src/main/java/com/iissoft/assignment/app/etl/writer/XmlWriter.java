package com.iissoft.assignment.app.etl.writer;

import com.iissoft.assignment.app.model.Employee;
import com.iissoft.assignment.app.model.EmployeeDto;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.OutputStream;
import java.util.List;

/**
 * Интерфейс для записи данных в XML
 */
public interface XmlWriter {
    /**
     * Записывает лист данных(работников) в XML по OutputStream(в нашем случае файл)
     *
     * @param  employees     лист дто работников
     * @param  outputStream  стрим, куда мы это все запишем
     * @throws TransformerException         Если произошла ошибка с трансформацией xml
     * @throws ParserConfigurationException Если что-то не так с конфигурацией парсера
     */
    void write(List<EmployeeDto> employees, OutputStream outputStream) throws TransformerException, ParserConfigurationException;
    /**
     * Генерирует DOM документ, для последующей его записи в XML
     *
     * @param  employees  Лист работников для записи
     * @return            документ DOM
     * @throws ParserConfigurationException Если что-то не так с конфигурацией парсера
     * @throws TransformerException         Если произошла ошибка с трансформацией xml
     */
    Document generateDomDocument(List<EmployeeDto> employees) throws ParserConfigurationException, TransformerException;

}
