package com.excilys.cdb.ui;

import java.util.Scanner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistenceImpl.ComputerDaoImpl;
import com.excilys.cdb.serviceImpl.CompanyServiceImpl;

public class Commands {

	Scanner scanner;
	CompanyServiceImpl listCompanies;
	ComputerDaoImpl listComputers;

	public Commands() {

		scanner = new Scanner(System.in);
		listCompanies = new CompanyServiceImpl();
		listComputers = new ComputerDaoImpl();
	}

	public void displayCommands() {

		System.out.println("Available commands :\n");
		System.out.println("\t1 - Display all companies\n");
		System.out.println("\t2 - Display all computers\n");
		System.out.println("\t3 - Display a single computer's details\n");
		System.out.println("\t4 - Create a computer\n");
		System.out.println("\t5 - Update a computer\n");
		System.out.println("\t6 - Delete a computer\n");
	}

	public int getUserInput() {

		boolean validInput = false;
		int userInput = 0;

		do {
			System.out.println("To display all the available commands, enter h");
			System.out.println("To shut down the application, enter q\n");
			System.out.println("Please enter a valid command (1-6) : ");

			if (scanner.hasNextInt()) {
				userInput = scanner.nextInt();

				if (userInput >= 1 && userInput <= 6) {
					validInput = true;
				} else {
					System.out.println("Wrong input : integer must be between 1 and 6\n");
				}
			} else {
				String userInputNotInteger = scanner.nextLine();
				switch (userInputNotInteger) {
				case "q":
					System.out.println("Goodbye :)");
					Runtime.getRuntime().exit(1);
					break;
				case "h":
					displayCommands();
					break;
				default:
					System.out.println("Wrong input : must be an integer\n");
				}
			}
		} while (!validInput);

		return userInput;

	}

	public void executeCommand(int userInput) {

		switch (userInput) {
		case 1:
			for (Company company : listCompanies.getCompanies()) {
				System.out.println("nom : " + company.getName());
			}

			break;
		case 2:
			listComputers.listComputers();
			break;
		case 3:

			break;
		case 4:

			break;
		case 5:
			break;
		case 6:
			break;
		default:
			System.out.println("Command unknown");
			break;
		}

	}

}