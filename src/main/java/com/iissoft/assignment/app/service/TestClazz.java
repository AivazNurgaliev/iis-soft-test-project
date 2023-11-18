package com.iissoft.assignment.app.service;

import com.iissoft.assignment.app.etl.reader.impl.DbReaderImpl;
import com.iissoft.assignment.app.etl.writer.impl.XmlWriterImpl;
import com.iissoft.assignment.app.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class TestClazz {
    private final DbReaderImpl reader;
    private final XmlWriterImpl xmlWriter;

    @Autowired
    public TestClazz(DbReaderImpl reader, XmlWriterImpl xmlWriter) {
        this.reader = reader;
        this.xmlWriter = xmlWriter;
    }

    public List<Employee> test() {
        return reader.read();
    }

    public void writeToXml() throws ParserConfigurationException, TransformerException {
        var list = test();
        // FIXME: 17.11.2023 // TODO: 17.11.2023 добавить fileoutputstream в try-with-resources 
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("employeese.xml"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        xmlWriter.write(list, fos);
    }
}
