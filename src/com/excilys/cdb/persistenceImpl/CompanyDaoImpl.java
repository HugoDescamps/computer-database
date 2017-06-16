package com.excilys.cdb.persistenceImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDao;

public class CompanyDaoImpl implements CompanyDao {

	@Override
	public List<Company> listCompanies() {

		List<Company> companiesList = new ArrayList<Company>();

		try (Connection connection = DataBaseConnector.connect();
				Statement listCompaniesStatement = connection.createStatement();
				ResultSet listCompaniesResult = listCompaniesStatement.executeQuery("SELECT * FROM company;");) {

			companiesList = CompanyMapper.getCompanies(listCompaniesResult);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Companies list retrieved");
		return companiesList;
	}
}