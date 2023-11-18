package com.iissoft.assignment.app.etl.writer;

import com.iissoft.assignment.app.model.Employee;
import com.iissoft.assignment.app.model.EmployeeDto;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.OutputStream;
import java.util.List;

public interface XmlWriter {
    void write(List<EmployeeDto> employees, OutputStream outputStream) throws TransformerException, ParserConfigurationException;
    Document generateDomDocument(List<EmployeeDto> employees) throws ParserConfigurationException, TransformerException;

}
