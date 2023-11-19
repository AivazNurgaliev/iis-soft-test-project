package com.iissoft.assignment.app.service.impl;

import com.iissoft.assignment.app.etl.processor.Processor;
import com.iissoft.assignment.app.etl.processor.impl.ProcessorImpl;
import com.iissoft.assignment.app.etl.reader.DbReader;
import com.iissoft.assignment.app.etl.reader.XmlReader;
import com.iissoft.assignment.app.etl.reader.impl.DbReaderImpl;
import com.iissoft.assignment.app.etl.reader.impl.XmlReaderImpl;
import com.iissoft.assignment.app.etl.writer.DbWriter;
import com.iissoft.assignment.app.etl.writer.XmlWriter;
import com.iissoft.assignment.app.etl.writer.impl.DbWriterImpl;
import com.iissoft.assignment.app.etl.writer.impl.XmlWriterImpl;
import com.iissoft.assignment.app.exception.EtlWriterException;
import com.iissoft.assignment.app.model.EmployeeDto;
import com.iissoft.assignment.app.model.NaturalKey;
import com.iissoft.assignment.app.service.EtlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Сервис для осуществления операций по синхронизации с БД и для выгрузки данных в XML
 * документация в интерфейсе
 * Имплементирует интерфейс EtlService
 */
@Service
public class EtlServiceImpl implements EtlService {
    private final DbReader dbReader;
    private final DbWriter dbWriter;
    private final XmlWriter xmlWriter;
    private final XmlReader xmlReader;
    private final Processor processor;
    private static final Logger logger = LoggerFactory.getLogger(EtlServiceImpl.class);
    @Autowired
    public EtlServiceImpl(DbReaderImpl dbReader, DbWriterImpl dbWriter,
                          XmlWriterImpl xmlWriter, XmlReaderImpl xmlReader, ProcessorImpl processor) {
        this.dbReader = dbReader;
        this.dbWriter = dbWriter;
        this.xmlWriter = xmlWriter;
        this.xmlReader = xmlReader;
        this.processor = processor;
    }

    @Override
    public void performSyncOperation(String filename) {
        logger.info("Performing sync operation: ");
        List<EmployeeDto> employeeXmlDtos = xmlReader.read(new File(filename));
        Map<NaturalKey, EmployeeDto> map = processor.process(employeeXmlDtos);
        List<EmployeeDto> employeeDbDtos = dbReader.read();
        dbWriter.write(map , employeeDbDtos);
        logger.info("Successfully performed sync operation");
    }

    @Override
    public void performDataUploadToXml(String filename) {
        logger.info("Performing data upload to xml: " + filename);
        List<EmployeeDto> employeeDbDtos = dbReader.read();
        try(FileOutputStream fos = new FileOutputStream(new File(filename))) {
            xmlWriter.write(employeeDbDtos, fos);
            logger.info("Successfully performed data upload to xml");
        } catch (IOException e) {
            logger.error("Something went wrong with xml writing", e.getMessage());
            throw new EtlWriterException("Something went wrong with xml writing", e);
        } catch (ParserConfigurationException e) {
            logger.error("Something went wrong with xml parsing", e.getMessage());
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            logger.error("Something went wrong with xml transformation", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
