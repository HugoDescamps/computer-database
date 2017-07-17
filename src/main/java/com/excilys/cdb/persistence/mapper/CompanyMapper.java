package com.excilys.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;

public class CompanyMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		
		return new Company(resultSet.getLong("company.id"), resultSet.getString("company.name"));
	}

}
