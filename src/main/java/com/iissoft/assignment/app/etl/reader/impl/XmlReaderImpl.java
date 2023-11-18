package com.iissoft.assignment.app.etl.reader.impl;

import com.iissoft.assignment.app.etl.reader.XmlReader;
import com.iissoft.assignment.app.model.Employee;
import com.iissoft.assignment.app.model.EmployeeDto;
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

@Component
public class XmlReaderImpl implements XmlReader {

    @Override
    public List<EmployeeDto> read(File file) {
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
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }
}
