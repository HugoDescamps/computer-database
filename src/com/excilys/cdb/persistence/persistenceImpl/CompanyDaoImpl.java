package com.excilys.cdb.persistence.persistenceImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDao;
import com.excilys.cdb.persistence.MyException;
import com.excilys.cdb.persistence.mapper.CompanyMapper;

public enum CompanyDaoImpl implements CompanyDao {
	INSTANCE;

	@Override
	public List<Company> listCompanies() {

		List<Company> companiesList = new ArrayList<Company>();

		try (Connection connection = DataBaseConnector.connect();
				Statement listCompaniesStatement = connection.createStatement();
				ResultSet listCompaniesResult = listCompaniesStatement.executeQuery("SELECT * FROM company;");) {

			companiesList = CompanyMapper.getCompanies(listCompaniesResult);

		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("Company DAO error in listCompanies method)");
		}
		System.out.println("Companies list retrieved");
		return companiesList;
	}
}