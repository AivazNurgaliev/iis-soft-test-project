package com.iissoft.assignment.app.etl.writer.impl;

import com.iissoft.assignment.app.etl.writer.Writer;
import com.iissoft.assignment.app.model.Employee;
import org.springframework.stereotype.Component;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@Component
public class XmlWriterImpl implements Writer {


    @Override
    public void write(List<Employee> employees, OutputStream output)
            throws TransformerException, ParserConfigurationException {
        Document doc = generateDomDocument(employees);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }

    @Override
    public Document generateDomDocument(List<Employee> employees) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("employees");
        doc.appendChild(rootElement);

        for (Employee employee : employees) {
            Element employeeElem = doc.createElement("employee");
            rootElement.appendChild(employeeElem);

            Element depCodeElem = doc.createElement("depCode");
            depCodeElem.setTextContent(employee.getDepCode());
            employeeElem.appendChild(depCodeElem);

            Element depJobElem = doc.createElement("depJob");
            depJobElem.setTextContent(employee.getDepJob());
            employeeElem.appendChild(depJobElem);

            Element descriptionElem = doc.createElement("description");
            descriptionElem.setTextContent(employee.getDescription());
            employeeElem.appendChild(descriptionElem);
        }

        return doc;
    }
}