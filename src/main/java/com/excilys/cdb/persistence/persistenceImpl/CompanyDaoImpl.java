package com.excilys.cdb.persistence.persistenceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.CompanyDao;
import com.excilys.cdb.persistence.MyException;
import com.excilys.cdb.persistence.mapper.CompanyMapper;

public enum CompanyDaoImpl implements CompanyDao {
	INSTANCE;

	@Override
	public Page<Company> listCompanies(int pageNumber, int pageSize) {

		Page<Company> companiesPage = new Page<Company>();

		List<Company> companiesList = new ArrayList<Company>();

		try (Connection connection = DataBaseConnector.connect();
				PreparedStatement listCompaniesStatement = connection
						.prepareStatement("SELECT * FROM company LIMIT ?,?;");) {

			listCompaniesStatement.setInt(1, (pageNumber - 1) * pageSize);

			listCompaniesStatement.setInt(2, pageSize);

			ResultSet listCompaniesResult = listCompaniesStatement.executeQuery();

			companiesList = CompanyMapper.getCompanies(listCompaniesResult);

			companiesPage.setObjectsList(companiesList);
			companiesPage.setNumber(pageNumber);
			companiesPage.setSize(pageSize);

		} catch (Exception e) {
			throw new MyException("Company DAO error in listCompanies method");
		}
		System.out.println("Companies list retrieved");

		return companiesPage;
	}

	public Company getCompany(long id) {
		Company company = null;

		ResultSet getCompanyResult = null;

		try (Connection connection = DataBaseConnector.connect();
				PreparedStatement getCompanyStatement = connection
						.prepareStatement("SELECT * FROM company WHERE id = ?;");) {

			getCompanyStatement.setLong(1, id);

			getCompanyResult = getCompanyStatement.executeQuery();

			company = CompanyMapper.getCompany(getCompanyResult);

		} catch (Exception e) {
			throw new MyException("Company DAO error in getCompany method");
		}

		return company;
	}
}