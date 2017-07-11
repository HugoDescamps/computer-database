package com.excilys.cdb.ui;

import com.excilys.cdb.service.serviceImpl.ComputerServiceImpl;

public class Main {

	public static void main(String[] args) {

		Commands commands = new Commands();

		commands.displayCommands();

		commands.waitForInput();
	}
}
