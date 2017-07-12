package com.excilys.cdb.ui;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDao.OrderColumn;
import com.excilys.cdb.persistence.ComputerDao.OrderWay;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class Commands {

	Scanner scanner;

	@Autowired
	@Qualifier("computerService")
	private ComputerService computerService;

	@Autowired
	@Qualifier("companyService")
	private CompanyService companyService;
	
	public ComputerService getComputerService() {
		return computerService;
	}

	public void setComputerService(ComputerService computerService) {
		this.computerService = computerService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public Commands() {

		scanner = new Scanner(System.in);
	}

	public void displayCommands() {

		System.out.println("Available commands :\n");
		System.out.println("\t1 - Display all companies\n");
		System.out.println("\t2 - Display all computers\n");
		System.out.println("\t3 - Display a single computer's details\n");
		System.out.println("\t4 - Create a computer\n");
		System.out.println("\t5 - Update a computer\n");
		System.out.println("\t6 - Delete a computer\n");
		System.out.println("\t7 - Delete a company\n");
	}

	public void waitForInput() {

		boolean validInput = false;
		int userInput = 0;

		do {
			System.out.println("\nTo display all the available commands, enter h");
			System.out.println("To shut down the application, enter q\n");
			System.out.println("Please enter a valid command (1-7) : ");

			if (scanner.hasNextInt()) {
				userInput = scanner.nextInt();
				scanner.nextLine();

				if (userInput >= 1 && userInput <= 7) {
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
		case 7:
			command7();
			break;
		default:
			System.out.println("Command unknown");
			break;
		}

	}

	public void command1() {

		boolean validInputCompanyNumber = false;
		int userInputCompanyNumber = 0;
		String displayNextPage = "";
		int pageNumber = 1;

		boolean endOfCommand = false;

		do {
			System.out.println("Enter how many companies you want to display");

			if (scanner.hasNextInt()) {
				userInputCompanyNumber = scanner.nextInt();
				scanner.nextLine();

				if (userInputCompanyNumber >= 0) {
					validInputCompanyNumber = true;

					System.out.println(companyService.getCompanies(pageNumber, userInputCompanyNumber).toString());
				} else {
					System.out.println("Wrong input : integer must be positive\n");
				}
			} else {
				scanner.nextLine();

				System.out.println("Wrong input : must be an integer\n");

			}
		} while (!validInputCompanyNumber);

		do {
			System.out.println("Press n to display next page, p to display previous page or q to quit");

			displayNextPage = scanner.nextLine();

			if (displayNextPage.equals("n")) {
				pageNumber++;
				System.out.println(companyService.getCompanies(pageNumber, userInputCompanyNumber).toString());
			} else if (displayNextPage.equals("p")) {
				if (pageNumber == 1) {
					System.out.println("Error : this is already the first page");
				} else {
					pageNumber--;
					System.out.println(companyService.getCompanies(pageNumber, userInputCompanyNumber).toString());
				}
			} else if (displayNextPage.equals("q")) {
				endOfCommand = true;
				this.waitForInput();
			} else {
				System.out.println("Wront input");
			}
		} while (!endOfCommand);

	}

	public void command2() {

		boolean validInputComputerNumber = false;
		int userInputComputerNumber = 0;
		String displayNextPage = "";
		int pageNumber = 1;

		boolean endOfCommand = false;

		do {
			System.out.println("Enter how many computers you want to display");

			if (scanner.hasNextInt()) {
				userInputComputerNumber = scanner.nextInt();
				scanner.nextLine();

				if (userInputComputerNumber >= 0) {
					validInputComputerNumber = true;

					System.out.println(computerService
							.getComputers(pageNumber, userInputComputerNumber, "", OrderColumn.NULL, OrderWay.ASC)
							.toString());
				} else {
					System.out.println("Wrong input : integer must be positive\n");
				}
			} else {
				scanner.nextLine();

				System.out.println("Wrong input : must be an integer\n");

			}
		} while (!validInputComputerNumber);

		do {
			System.out.println("Press n to display next page, p to display previous page or q to quit");

			displayNextPage = scanner.nextLine();

			if (displayNextPage.equals("n")) {
				pageNumber++;
				System.out.println(computerService
						.getComputers(pageNumber, userInputComputerNumber, "", OrderColumn.NULL, OrderWay.ASC)
						.toString());
			} else if (displayNextPage.equals("p")) {
				if (pageNumber == 1) {
					System.out.println("Error : this is already the first page");
				} else {
					pageNumber--;
					System.out.println(computerService
							.getComputers(pageNumber, userInputComputerNumber, "", OrderColumn.NULL, OrderWay.ASC)
							.toString());
				}
			} else if (displayNextPage.equals("q")) {
				endOfCommand = true;
				this.waitForInput();
			} else {
				System.out.println("Wront input");
			}
		} while (!endOfCommand);
	}

	public void command3() {

		boolean validInputComputerId = false;
		int userInputComputerId = 0;

		do {
			System.out.println("Enter the computer's id you want to display");

			if (scanner.hasNextInt()) {
				userInputComputerId = scanner.nextInt();
				scanner.nextLine();

				if (userInputComputerId >= 1) {
					validInputComputerId = true;
					System.out.println(computerService.getComputer(userInputComputerId).toString());
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

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		boolean validInputComputerName = false;
		boolean validInputComputerIntroducedDate = false;
		boolean validInputComputerDiscontinuedDate = false;
		boolean validInputCompanyId = false;

		String userInputComputerName;
		String userInputComputerIntroducedDate;
		LocalDate introducedDate = null;
		String userInputComputerDiscontinuedDate;
		LocalDate discontinuedDate = null;
		int userInputCompanyId = 0;
		Company company = null;

		do {

			System.out.println("Enter the computer's name you want to create");

			userInputComputerName = scanner.nextLine();

			if (!userInputComputerName.equals("")) {
				validInputComputerName = true;
			} else {
				System.out.println("Wrong input : computer must have a name");
			}

		} while (!validInputComputerName);

		do {
			System.out.println("Enter the computer's introduced date (yyyy-MM-dd or '' for null)");

			userInputComputerIntroducedDate = scanner.nextLine();

			if (userInputComputerIntroducedDate.equals("")) {
				validInputComputerIntroducedDate = true;
			} else {
				if (userInputComputerIntroducedDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
					try {
						introducedDate = LocalDate.parse(userInputComputerIntroducedDate, formatter);
						validInputComputerIntroducedDate = true;
					} catch (DateTimeException e) {
						System.out.println("Wrong input : please enter a correct date");
					}
				} else {
					System.out.println("Wront input : please enter a date");
				}
			}

		} while (!validInputComputerIntroducedDate);

		do {
			System.out.println("Enter the computer's discontinued date (yyyy-MM-dd or '' for null)");

			userInputComputerDiscontinuedDate = scanner.nextLine();

			if (userInputComputerDiscontinuedDate.equals("")) {
				validInputComputerDiscontinuedDate = true;
			} else {
				if (userInputComputerDiscontinuedDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
					try {
						discontinuedDate = LocalDate.parse(userInputComputerDiscontinuedDate, formatter);
						if (introducedDate == null || discontinuedDate.isAfter(introducedDate)) {
							validInputComputerDiscontinuedDate = true;
						} else {
							System.out.println("Wrong input : discontinued date must be after introduced date");
						}
					} catch (DateTimeException e) {
						System.out.println("Wrong input : please enter a correct date");
					}
				} else {
					System.out.println("Wront input : please enter a date");
				}
			}

		} while (!validInputComputerDiscontinuedDate);

		do {
			System.out.println("Enter the company's id that owns the computer (0 for null)");

			if (scanner.hasNextInt()) {
				userInputCompanyId = scanner.nextInt();
				scanner.nextLine();

				if (userInputCompanyId >= 0) {
					if (userInputCompanyId == 0) {
						validInputCompanyId = true;
					} else {
						company = companyService.getCompany(userInputCompanyId);
						if (company == null) {
							System.out.println("Wrong input : this company doesn't exist");
						} else {
							validInputCompanyId = true;
						}
					}
				} else {
					System.out.println("Wrong input : must be positive\n");
				}
			} else {
				scanner.nextLine();

				System.out.println("Wrong input : must be an integer\n");
			}

		} while (!validInputCompanyId);

		computer.setName(userInputComputerName);

		if (introducedDate != null) {
			computer.setIntroduced(introducedDate);
		}

		if (discontinuedDate != null) {
			computer.setDiscontinued(discontinuedDate);
		}

		if (company != null) {
			computer.setCompany(company);
		}

		System.out.println(computerService.addComputer(computer).toString());

		this.waitForInput();

	}

	public void command5() {

		Computer computer = new Computer();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		boolean validInputComputerId = false;
		boolean validInputComputerName = false;
		boolean validInputComputerIntroducedDate = false;
		boolean validInputComputerDiscontinuedDate = false;
		boolean validInputCompanyId = false;

		int userInputComputerId;
		String userInputComputerName;
		String userInputComputerIntroducedDate;
		LocalDate introducedDate = null;
		String userInputComputerDiscontinuedDate;
		LocalDate discontinuedDate = null;
		int userInputCompanyId = 0;
		Company company = null;

		do {
			System.out.println("Enter the computer's id you want to update");

			if (scanner.hasNextInt()) {
				userInputComputerId = scanner.nextInt();
				scanner.nextLine();

				if (userInputComputerId >= 1) {
					computer = computerService.getComputer(userInputComputerId);
					if (computer == null) {
						System.out.println("Wrong input : this computer doesn't exist");
					} else {
						validInputComputerId = true;
					}
				} else {
					System.out.println("Wrong input : must be greater than 1\n");
				}
			} else {
				scanner.nextLine();

				System.out.println("Wrong input : must be an integer\n");
			}

		} while (!validInputComputerId);

		do {

			System.out.println("Enter the computer's new name");

			userInputComputerName = scanner.nextLine();

			if (!userInputComputerName.equals("")) {
				computer.setName(userInputComputerName);
				validInputComputerName = true;
			} else {
				System.out.println("Wrong input : computer must have a name");
			}

		} while (!validInputComputerName);

		do {
			System.out.println("Enter the computer's new introduced date (yyyy-MM-dd or '' for null)");

			userInputComputerIntroducedDate = scanner.nextLine();

			if (userInputComputerIntroducedDate.equals("")) {
				computer.setIntroduced(null);
				validInputComputerIntroducedDate = true;
			} else {
				if (userInputComputerIntroducedDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
					try {
						introducedDate = LocalDate.parse(userInputComputerIntroducedDate, formatter);
						computer.setIntroduced(introducedDate);
						validInputComputerIntroducedDate = true;
					} catch (DateTimeException e) {
						System.out.println("Wrong input : please enter a correct date");
					}
				} else {
					System.out.println("Wront input : please enter a date");
				}
			}

		} while (!validInputComputerIntroducedDate);

		do {
			System.out.println("Enter the computer's discontinued date (yyyy-MM-dd or '' for null)");

			userInputComputerDiscontinuedDate = scanner.nextLine();

			if (userInputComputerDiscontinuedDate.equals("")) {
				computer.setDiscontinued(null);
				validInputComputerDiscontinuedDate = true;
			} else {
				if (userInputComputerDiscontinuedDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
					try {
						discontinuedDate = LocalDate.parse(userInputComputerDiscontinuedDate, formatter);
						if (introducedDate == null || discontinuedDate.isAfter(introducedDate)) {
							computer.setDiscontinued(discontinuedDate);
							validInputComputerDiscontinuedDate = true;
						} else {
							System.out.println("Wrong input : discontinued date must be after introduced date");
						}
					} catch (DateTimeException e) {
						System.out.println("Wrong input : please enter a correct date");
					}
				} else {
					System.out.println("Wront input : please enter a date");
				}
			}

		} while (!validInputComputerDiscontinuedDate);

		do {
			System.out.println("Enter the company's id that owns the computer (0 for null)");

			if (scanner.hasNextInt()) {
				userInputCompanyId = scanner.nextInt();
				scanner.nextLine();

				if (userInputCompanyId >= 0) {
					if (userInputCompanyId == 0) {
						computer.setCompany(null);
						validInputCompanyId = true;
					} else {
						company = companyService.getCompany(userInputCompanyId);
						if (company == null) {
							System.out.println("Wrong input : this company doesn't exist");
						} else {
							computer.setCompany(company);
							validInputCompanyId = true;
						}
					}
				} else {
					System.out.println("Wrong input : must be positive\n");
				}
			} else {
				scanner.nextLine();

				System.out.println("Wrong input : must be an integer\n");
			}

		} while (!validInputCompanyId);

		computer.setName(userInputComputerName);

		if (introducedDate != null) {
			computer.setIntroduced(introducedDate);
		}

		if (discontinuedDate != null) {
			computer.setDiscontinued(discontinuedDate);
		}

		if (company != null) {
			computer.setCompany(company);
		}
		computerService.updateComputer(computer);

		System.out.println("The computer has been updated to : ");
		System.out.println(computer.toString());

		this.waitForInput();

	}

	public void command6() {

		Computer computer;

		boolean validInputComputerId = false;
		int userInputComputerId = 0;

		do {
			System.out.println("Enter the computer's id you want to remove");

			if (scanner.hasNextInt()) {
				userInputComputerId = scanner.nextInt();
				scanner.nextLine();
				if (userInputComputerId >= 1) {
					computer = computerService.getComputer(userInputComputerId);
					if (computer == null) {
						System.out.println("Wrong input : this computer doesn't exist\n");
					} else {
						validInputComputerId = true;
						computerService.removeComputer(userInputComputerId);
					}
				} else {
					System.out.println("Wrong input : integer must be greater than 0\n");
				}
			} else {
				scanner.nextLine();

				System.out.println("Wrong input : must be an integer\n");

			}
		} while (!validInputComputerId);

		System.out.println("The computer of id " + userInputComputerId + " has been removed");

		this.waitForInput();

	}

	public void command7() {

		Company company;

		boolean validInputCompanyId = false;
		int userInputCompanyId = 0;

		do {
			System.out.println("Enter the company's id you want to remove");

			if (scanner.hasNextInt()) {
				userInputCompanyId = scanner.nextInt();
				scanner.nextLine();
				if (userInputCompanyId >= 1) {
					company = companyService.getCompany(userInputCompanyId);
					if (company == null) {
						System.out.println("Wrong input : this company doesn't exist\n");
					} else {
						validInputCompanyId = true;
						companyService.removeCompany(userInputCompanyId);
					}
				} else {
					System.out.println("Wrong input : integer must be greater than 0\n");
				}
			} else {
				scanner.nextLine();

				System.out.println("Wrong input : must be an integer\n");

			}
		} while (!validInputCompanyId);

		System.out.println("The company of id " + userInputCompanyId + " and its computers have been removed");

		this.waitForInput();

	}

}
