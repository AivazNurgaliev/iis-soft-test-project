package com.iissoft.assignment.app;

import com.iissoft.assignment.app.service.impl.EtlServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class IisSoftApplication {
	private final EtlServiceImpl etlService1;


	public IisSoftApplication(EtlServiceImpl etlService1) {
		this.etlService1 = etlService1;
	}

	public static void main(String[] args) {
		//SpringApplication.run(IisSoftApplication.class, args);

		ApplicationContext context = SpringApplication.run(IisSoftApplication.class, args);

		if (args.length > 1 && args[0].equals("upload")) {
			EtlServiceImpl etlService = context.getBean(EtlServiceImpl.class);
			etlService.performDataUploadToXml(args[1]);
			System.exit(0);
		}

		if (args.length > 1 && args[0].equals("sync")) {
			EtlServiceImpl etlService = context.getBean(EtlServiceImpl.class);
			etlService.performSyncOperation(args[1]);
			System.exit(0);
		}
	}

	@GetMapping("/sync")
	public void test() {
		etlService1.performSyncOperation("test3.xml");
	}


	@GetMapping("/uploadToXml")
	public void writeToXml() {
		etlService1.performDataUploadToXml("employeese.xml");
	}

}
