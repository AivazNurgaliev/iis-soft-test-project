package com.iissoft.assignment.app.etl.writer;

import com.iissoft.assignment.app.model.Employee;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.OutputStream;
import java.util.List;

public interface Writer {
    Document generateDomDocument(List<Employee> employees) throws ParserConfigurationException, TransformerException;
    void write(List<Employee> employees, OutputStream outputStream) throws TransformerException, ParserConfigurationException;
}
