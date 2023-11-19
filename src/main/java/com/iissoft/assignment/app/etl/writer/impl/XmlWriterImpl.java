package com.iissoft.assignment.app.etl.writer.impl;

import com.iissoft.assignment.app.etl.writer.XmlWriter;
import com.iissoft.assignment.app.model.EmployeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.util.List;

/**
 * Имплементация интерфейса XmlWriter, документация находится в интерфейсе
 */
@Component
public class XmlWriterImpl implements XmlWriter {
    private static final Logger logger = LoggerFactory.getLogger(XmlWriterImpl.class);
    @Override
    public void write(List<EmployeeDto> employees, OutputStream output)
            throws TransformerException, ParserConfigurationException {
        logger.info("Starting writing to xml file");
        Document doc = generateDomDocument(employees);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

        logger.info("Successfully wrote to xml file");
    }

    @Override
    public Document generateDomDocument(List<EmployeeDto> employees) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("employees");
        doc.appendChild(rootElement);

        for (EmployeeDto employee : employees) {
            Element employeeElem = doc.createElement("employee");
            rootElement.appendChild(employeeElem);

            appendEmployeeToXML(doc, employee, employeeElem);
        }

        return doc;
    }

    private static void appendEmployeeToXML(Document doc, EmployeeDto employee, Element employeeElem) {
        Element depCodeElem = doc.createElement("depCode");
        depCodeElem.setTextContent(employee.getDepCode());
        employeeElem.appendChild(depCodeElem);

        Element depJobElem = doc.createElement("depJob");
        depJobElem.setTextContent(employee.getDepJob());
        employeeElem.appendChild(depJobElem);

        String description = employee.getDescription();
        if(description != null) {
            Element descriptionElem = doc.createElement("description");
            descriptionElem.setTextContent(employee.getDescription());
            employeeElem.appendChild(descriptionElem);
        }
    }
}
