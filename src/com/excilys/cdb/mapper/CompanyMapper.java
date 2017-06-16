package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;

public class CompanyMapper {

	public static Company extractCompany(ResultSet resultSetCompany) {
		
		Company company = null;

		try {
			company = new Company(resultSetCompany.getLong("id"), resultSetCompany.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}

	public static Company getCompany(ResultSet resultSetCompany) {

		Company company = null;

		try {
			while (resultSetCompany.next()) {

				company = extractCompany(resultSetCompany);
			}
		} catch (SQLException e) {

			e.printStackTrace();
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
			e.printStackTrace();
		}
		return companiesList;
	}

}
