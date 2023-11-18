package com.iissoft.assignment.app;

import com.iissoft.assignment.app.model.Employee;
import com.iissoft.assignment.app.model.EmployeeDto;
import com.iissoft.assignment.app.model.NaturalKey;
import com.iissoft.assignment.app.service.TestClazz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.List;
import java.util.Map;

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

	@GetMapping("/test")
	public void test() {
		test.sh();
	}

	@GetMapping("/read")
	public List<EmployeeDto> readFromDb() {
		return test.test();
	}

	@GetMapping("/write")
	public void writeToXml() throws ParserConfigurationException, TransformerException {
		test.writeToXml();
	}

	@GetMapping("/xml/read")
	public List<EmployeeDto> readFromXml() throws ParserConfigurationException, TransformerException {
		return test.readFromXml();
	}


	@GetMapping("/process")
	public Map<NaturalKey, EmployeeDto> process() {
		return test.process();
	}

}
