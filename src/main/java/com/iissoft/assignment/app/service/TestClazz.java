package com.iissoft.assignment.app.service;

import com.iissoft.assignment.app.etl.processor.impl.ProcessorImpl;
import com.iissoft.assignment.app.etl.reader.impl.DbReaderImpl;
import com.iissoft.assignment.app.etl.reader.impl.XmlReaderImpl;
import com.iissoft.assignment.app.etl.writer.impl.DbWriterImpl;
import com.iissoft.assignment.app.etl.writer.impl.XmlWriterImpl;
import com.iissoft.assignment.app.model.EmployeeDto;
import com.iissoft.assignment.app.model.NaturalKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

@Service
public class TestClazz {
    private final DbReaderImpl reader;
    private final DbWriterImpl dbWriter;

    private final XmlWriterImpl xmlWriter;
    private final XmlReaderImpl xmlReader;
    private final ProcessorImpl processor;

    @Autowired
    public TestClazz(DbReaderImpl reader, DbWriterImpl dbWriter, XmlWriterImpl xmlWriter, XmlReaderImpl xmlReader, ProcessorImpl processor) {
        this.reader = reader;
        this.dbWriter = dbWriter;
        this.xmlWriter = xmlWriter;
        this.xmlReader = xmlReader;
        this.processor = processor;
    }

    public List<EmployeeDto> test() {
        return reader.read();
    }

    public void sh() {
        var list = xmlReader.read(new File("employeese.xml"));
        var t = processor.process(list);
        var c = reader.read();
        dbWriter.write(t , c);
    }

    public Map<NaturalKey, EmployeeDto> process() {
        return processor.process(readFromXml());
    }

    public List<EmployeeDto> readFromXml() {
        var c = xmlReader.read(new File("employeese.xml"));
        System.out.println(c);
        return c;
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
