package com.excilys.cdb.ui;

import com.excilys.cdb.service.serviceImpl.ComputerServiceImpl;

public class Main {

	public static void main(String[] args) {

		/*Commands commands = new Commands();

		commands.displayCommands();

		commands.waitForInput();*/
		
		ComputerServiceImpl test = ComputerServiceImpl.INSTANCE;
		
		for (int i = 0; i<4; i++) {
			System.out.println(test.getComputers(1, 50, "", "computerDesc").getObjectsList().get(i).toString());
		}

	}
}