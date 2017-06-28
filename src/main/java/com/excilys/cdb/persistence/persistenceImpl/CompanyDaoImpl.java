package com.excilys.cdb.persistence.persistenceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.CompanyDao;
import com.excilys.cdb.persistence.DaoException;
import com.excilys.cdb.persistence.mapper.CompanyMapper;

public enum CompanyDaoImpl implements CompanyDao {
	INSTANCE;

	static final Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

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

		} catch (SQLException e) {
			throw new DaoException("Company DAO error in listCompanies method " + e.getMessage());
		}

		logger.info("Companies list retrieved");

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

		} catch (SQLException e) {
			throw new DaoException("Company DAO error in getCompany method " + e.getMessage());
		}

		logger.info("Company retrieved");
		return company;
	}

	@Override
	public List<Company> listCompanies() {

		List<Company> companiesList = new ArrayList<Company>();

		ResultSet listCompaniesResult = null;

		try (Connection connection = DataBaseConnector.connect();
				Statement listCompaniesStatement = connection.createStatement()) {

			listCompaniesResult = listCompaniesStatement.executeQuery("SELECT * FROM company ORDER BY name;");

			companiesList = CompanyMapper.getCompanies(listCompaniesResult);

		} catch (SQLException e) {
			throw new DaoException("Company DAO error in listCompanies method " + e.getMessage());
		}

		logger.info("Companies list retrieved");

		return companiesList;
	}
}