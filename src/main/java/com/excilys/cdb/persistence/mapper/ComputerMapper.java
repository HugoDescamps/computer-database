package com.excilys.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoException;

public class ComputerMapper {

	public static Computer extractComputer(ResultSet resultSetComputer) {

		Company company = null;
		Computer computer = null;

		try {
			company = CompanyMapper.extractCompany(resultSetComputer);

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
			throw new DaoException("Mapper error in extractComputer method " + e.getMessage());
		}
		return computer;
	}

	public static Computer getComputer(ResultSet resultSetComputer) {

		Computer computer = null;

		try {
			if (resultSetComputer.next()) {
				computer = extractComputer(resultSetComputer);
			}
		} catch (SQLException e) {
			throw new DaoException("Mapper error in getComputer method " + e.getMessage());
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
			throw new DaoException("Mapper error in getComputers method " + e.getMessage());
		}
		return computersList;
	}

	public static int countComputers(ResultSet resultSetCount) {
		int count = 0;

		try {
			if (resultSetCount.next()) {
				count = resultSetCount.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException("Mapper error in countComputers method " + e.getMessage());
		}
		return count;
	}

}
