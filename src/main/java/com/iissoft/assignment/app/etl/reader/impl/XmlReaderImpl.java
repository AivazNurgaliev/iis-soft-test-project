package com.iissoft.assignment.app.etl.reader.impl;

import com.iissoft.assignment.app.etl.reader.XmlReader;
import com.iissoft.assignment.app.exception.EtlReaderException;
import com.iissoft.assignment.app.model.EmployeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Имплементация интерфейса XmlReader, документация находится в интерфейсе
 */
@Component
public class XmlReaderImpl implements XmlReader {
    private static final Logger logger = LoggerFactory.getLogger(XmlReaderImpl.class);

    @Override
    public List<EmployeeDto> read(File file) {
        logger.info("Starting reading from xml file: {}", file.getName());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        List<EmployeeDto> employees = new ArrayList<>();
        try {
            //Позволяет обрабатывать XML безопасно
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("employee");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    EmployeeDto employee = new EmployeeDto();
                    Element element = (Element) node;

                    employee.setDepCode(element.getElementsByTagName("depCode").item(0).getTextContent());
                    employee.setDepJob(element.getElementsByTagName("depJob").item(0).getTextContent());

                    if(element.getElementsByTagName("description").item(0) != null) {
                        employee.setDescription(element.getElementsByTagName("description").item(0).getTextContent());
                    } else {
                        employee.setDescription(null);
                    }

                    employees.add(employee);
                }
            }

            logger.info("Successfully read from xml file: {}", file.getName());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("Failed to read from xml file: {}", file.getName());
            throw new EtlReaderException("Failed to read from xml file: " + file.getName(), e);
        }

        return employees;
    }
}
