package com.excilys.cdb.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb.persistence.ComputerDao;

public class Main {

	@Autowired
	static ComputerDao test;

	public static void main(String[] args) {

		/*
		 * Commands commands = new Commands();
		 * 
		 * commands.displayCommands();
		 * 
		 * commands.waitForInput();
		 */

		System.out.println(test.countComputers(""));
	}
}
