package com.excilys.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

		Company company = new Company(resultSet.getLong("company.id"), resultSet.getString("company.name"));

		LocalDate introducedDate = null;
		LocalDate discontinuedDate = null;

		if (resultSet.getTimestamp("computer.introduced") != null) {
			introducedDate = resultSet.getTimestamp("computer.introduced").toLocalDateTime().toLocalDate();
		}

		if (resultSet.getTimestamp("computer.discontinued") != null) {
			discontinuedDate = resultSet.getTimestamp("computer.discontinued").toLocalDateTime().toLocalDate();
		}

		Computer computer = new Computer(resultSet.getLong("computer.id"), resultSet.getString("computer.name"),
				introducedDate, discontinuedDate, company);

		return computer;
	}

}
