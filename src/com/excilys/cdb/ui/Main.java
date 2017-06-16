package com.excilys.cdb.ui;

public class Main {

	public static void main(String[] args) {
		
		Commands commands = new Commands();
		
		commands.displayCommands();
		
		int userInput = commands.getUserInput();

		commands.executeCommand(userInput);

	}
}