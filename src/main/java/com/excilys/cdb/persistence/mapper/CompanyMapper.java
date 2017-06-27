package com.excilys.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DaoException;

public class CompanyMapper {

	static final Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

	public static Company extractCompany(ResultSet resultSetCompany) {

		Company company = null;

		try {
			company = new Company(resultSetCompany.getLong("company.id"), resultSetCompany.getString("company.name"));
		} catch (SQLException e) {
			throw new DaoException("Mapper error in extractCompany method " + e.getMessage());
		}
		return company;
	}

	public static Company getCompany(ResultSet resultSetCompany) {

		Company company = null;

		try {
			if (resultSetCompany.next()) {
				company = extractCompany(resultSetCompany);
			}
		} catch (SQLException e) {
			throw new DaoException("Mapper error in getCompanies method " + e.getMessage());
		}

		return company;

	}

	public static List<Company> getCompanies(ResultSet resultSetCompanies) {

		List<Company> companiesList = new ArrayList<Company>();

		try {
			while (resultSetCompanies.next()) {
				companiesList.add(extractCompany(resultSetCompanies));
			}
		} catch (SQLException e) {
			throw new DaoException("Mapper error in getCompanies method " + e.getMessage());
		}
		return companiesList;
	}

}
