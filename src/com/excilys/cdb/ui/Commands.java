package com.excilys.cdb.ui;

import java.sql.Timestamp;
import java.util.Scanner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.serviceImpl.CompanyServiceImpl;
import com.excilys.cdb.service.serviceImpl.ComputerServiceImpl;

public class Commands {

	Scanner scanner;
	CompanyServiceImpl companyService;
	ComputerServiceImpl computerService;

	public Commands() {

		scanner = new Scanner(System.in);
		companyService = CompanyServiceImpl.INSTANCE;
		computerService = ComputerServiceImpl.INSTANCE;
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

	public void waitForInput() {

		boolean validInput = false;
		int userInput = 0;

		do {
			System.out.println("\nTo display all the available commands, enter h");
			System.out.println("To shut down the application, enter q\n");
			System.out.println("Please enter a valid command (1-6) : ");

			if (scanner.hasNextInt()) {
				userInput = scanner.nextInt();
				scanner.nextLine();

				if (userInput >= 1 && userInput <= 6) {
					validInput = true;
					this.executeCommand(userInput);
					System.out.println(userInput);
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
	}

	public void executeCommand(int userInput) {

		switch (userInput) {
		case 1:
			command1();
			break;
		case 2:
			command2();
			break;
		case 3:
			command3();
			break;
		case 4:
			command4();
			break;
		case 5:
			command5();
			break;
		case 6:
			command6();
			break;
		default:
			System.out.println("Command unknown");
			break;
		}

	}

	public void command1() {
		for (Company company : companyService.getCompanies()) {
			System.out.println(company.toString());
		}
		this.waitForInput();
	}

	public void command2() {
		for (Computer computer : computerService.getComputers()) {
			System.out.println(computer.toString());
		}
		this.waitForInput();
	}

	public void command3() {

		boolean validInputComputerId = false;
		int userInputComputerId = 0;

		do {
			System.out.println("Enter the computer's id you want to display");

			if (scanner.hasNextInt()) {
				userInputComputerId = scanner.nextInt();

				if (userInputComputerId >= 1) {
					validInputComputerId = true;
					try {
						System.out.println(computerService.getComputer(userInputComputerId).toString());
					} catch (Exception e) {

					}

				} else {
					System.out.println("Wrong input : integer must be greater than 0\n");
				}
			} else {
				scanner.nextLine();

				System.out.println("Wrong input : must be an integer\n");

			}
		} while (!validInputComputerId);

		this.waitForInput();

	}

	public void command4() {

		Computer computer = new Computer();

		boolean validInputComputerName = false;
		String userInputComputerName;
		
		Timestamp userInputComputerIntroducedDate;
		Timestamp userInputComputerDiscontinuedDate;
		int userInputCompanyId;

		do {
			System.out.println("Enter the computer's name you want to create");

			userInputComputerName = scanner.nextLine();
			
			if (!userInputComputerName.equals("")) {
				validInputComputerName = true;
				//TODO
				/*try {
					System.out.println(computerService.getComputer(userInputComputerId).toString());
				} catch (Exception e) {

				}*/

			} else {
				System.out.println("Wrong input : computer must have a name");
			}

		} while (!validInputComputerName);

		System.out.println("L'ordinateur suivante a été ajouté :");
		System.out.println(computerService.addComputer(computer).toString());

		this.waitForInput();

	}

	public void command5() {

	}

	public void command6() {

	}

}