package com.excilys.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.MyException;

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
		} catch (Exception e) {
			throw new MyException("Mapper error in extractComputer method");
		}
		return computer;
	}

	public static Computer getComputer(ResultSet resultSetComputer) {

		Computer computer = null;

		try {
			if (resultSetComputer.next()) {
				computer = extractComputer(resultSetComputer);
			} else {
				throw new MyException("No computer found for this id");
			}
		} catch (Exception e) {
			throw new MyException("Mapper error in getComputer method");
		}

		return computer;
	}

	public static List<Computer> getComputers(ResultSet resultSetComputers) {

		List<Computer> computersList = new ArrayList<Computer>();

		try {
			while (resultSetComputers.next()) {
				computersList.add(extractComputer(resultSetComputers));
			}
		} catch (Exception e) {
			throw new MyException("Mapper error in getComputers method");
		}
		return computersList;
	}

	public static int countComputers(ResultSet resultSetCount) {
		int count = 0;

		try {
			if (resultSetCount.next()) {
				count = resultSetCount.getInt(1);
			}
		} catch (Exception e) {
			throw new MyException("Mapper error in countComputers method");
		}
		return count;
	}

}
