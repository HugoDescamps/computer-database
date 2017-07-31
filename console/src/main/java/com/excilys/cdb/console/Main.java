package com.excilys.cdb.console;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.console.config.CLIConfig;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class Main {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(CLIConfig.class);
		context.refresh();

		Commands commands = new Commands();

		commands.setCompanyService(context.getBean(CompanyService.class));
		commands.setComputerService(context.getBean(ComputerService.class));

		commands.displayCommands();

		commands.waitForInput();
		context.close();

	}
}
