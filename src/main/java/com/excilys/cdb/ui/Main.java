package com.excilys.cdb.ui;

import java.util.logging.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.CLIConfig;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class Main {

	public static void main(String[] args) {
		
		final Logger log = Logger.getLogger(Main.class.getName());

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		log.warning("c'est parti");
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
