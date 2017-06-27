package com.excilys.cdb.ui;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.persistenceImpl.CompanyDaoImpl;
import com.excilys.cdb.persistence.persistenceImpl.ComputerDaoImpl;

public class Main {

	public static void main(String[] args) {

		/*Commands commands = new Commands();

		commands.displayCommands();

		commands.waitForInput();*/
		
		ComputerDaoImpl pouet = ComputerDaoImpl.INSTANCE;
		
		pouet.addComputer(new Computer());

	}
}