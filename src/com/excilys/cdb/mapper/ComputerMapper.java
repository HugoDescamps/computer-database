package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper {

	public static Computer extractComputer(ResultSet resultSetComputer) {

		Company company = null;
		Computer computer = null;

		try {
			company = new Company(resultSetComputer.getLong("company.id"), resultSetComputer.getString("company.name"));

			LocalDate introducedDate = null;
			LocalDate discontinuedDate = null;

			if (resultSetComputer.getTimestamp("computer.introduced") != null) {
				introducedDate = resultSetComputer.getTimestamp("computer.introduced").toLocalDateTime().toLocalDate();
			}

			if (resultSetComputer.getTimestamp("computer.discontinued") != null) {
				discontinuedDate = resultSetComputer.getTimestamp("computer.discontinued").toLocalDateTime()
						.toLocalDate();
			}

			computer = new Computer(resultSetComputer.getLong("computer.id"),
					resultSetComputer.getString("computer.name"), introducedDate, discontinuedDate, company);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

	public static Computer getComputer(ResultSet resultSetComputer) {

		Computer computer = null;

		try {
			while (resultSetComputer.next()) {

				computer = extractComputer(resultSetComputer);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return computer;
	}

	public static List<Computer> getComputers(ResultSet resultSetComputers) {

		List<Computer> computersList = new ArrayList<Computer>();

		try {
			while (resultSetComputers.next()) {
				computersList.add(extractComputer(resultSetComputers));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computersList;
	}
}
