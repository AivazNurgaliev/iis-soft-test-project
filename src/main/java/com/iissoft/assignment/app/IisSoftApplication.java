package com.iissoft.assignment.app;

import com.iissoft.assignment.app.model.Employee;
import com.iissoft.assignment.app.service.TestClazz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.List;

@SpringBootApplication
@RestController
public class IisSoftApplication {
	private final TestClazz test;

	@Autowired
	public IisSoftApplication(TestClazz test) {
		this.test = test;
	}

	public static void main(String[] args) {
		SpringApplication.run(IisSoftApplication.class, args);
	}

	@GetMapping("/read")
	public List<Employee> readFromDb() {
		return test.test();
	}

	@GetMapping("/write")
	public void writeToXml() throws ParserConfigurationException, TransformerException {
		test.writeToXml();
	}

}
