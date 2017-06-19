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
			e.printStackTrace();
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
				throw new MyException("No result found for this search");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
			throw new MyException("Mapper error in getComputers method");
		}
		return computersList;
	}
	
	
}
