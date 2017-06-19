package com.excilys.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.MyException;

public class CompanyMapper {

	public static Company extractCompany(ResultSet resultSetCompany) {

		Company company = null;

		try {
			company = new Company(resultSetCompany.getLong("id"), resultSetCompany.getString("name"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("Mapper error in extractCompany method");
		}
		return company;
	}

	public static Company getCompany(ResultSet resultSetCompany) {

		Company company = null;

		try {
			while (resultSetCompany.next()) {
				company = extractCompany(resultSetCompany);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("Mapper error in getCompany method");
		}

		return company;

	}

	public static List<Company> getCompanies(ResultSet resultSetCompanies) {

		List<Company> companiesList = new ArrayList<Company>();

		try {
			while (resultSetCompanies.next()) {
				companiesList.add(extractCompany(resultSetCompanies));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("Mapper error in getCompanies method");
		}
		return companiesList;
	}

}
