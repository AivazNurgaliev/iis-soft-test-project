package com.iissoft.assignment.app;

import com.iissoft.assignment.app.service.impl.EtlServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class IisSoftApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(IisSoftApplication.class, args);

		if (args.length > 1 && args[0].equals("upload")) {
			EtlServiceImpl etlService = context.getBean(EtlServiceImpl.class);
			etlService.performDataUploadToXml(args[1]);
			//System.exit(0);
		}

		if (args.length > 1 && args[0].equals("sync")) {
			EtlServiceImpl etlService = context.getBean(EtlServiceImpl.class);
			etlService.performSyncOperation(args[1]);
			//System.exit(0);
		}
	}
}
