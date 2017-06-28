package com.excilys.cdb.persistence.persistenceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
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

		Connection connection = null;
		PreparedStatement listCompaniesStatement = null;

		try {

			connection = DataBaseConnector.connect();

			listCompaniesStatement = connection.prepareStatement("SELECT * FROM company LIMIT ?,?;");

			listCompaniesStatement.setInt(1, (pageNumber - 1) * pageSize);

			listCompaniesStatement.setInt(2, pageSize);

			ResultSet listCompaniesResult = listCompaniesStatement.executeQuery();

			companiesList = CompanyMapper.getCompanies(listCompaniesResult);

			companiesPage.setObjectsList(companiesList);
			companiesPage.setNumber(pageNumber);
			companiesPage.setSize(pageSize);

			connection.close();

		} catch (SQLException e) {
			throw new DaoException("Company DAO error in listCompanies method " + e.getMessage());
		}

		logger.info("Companies list retrieved");

		return companiesPage;
	}

	public Company getCompany(long id) {
		Company company = null;

		ResultSet getCompanyResult = null;
		Connection connection = null;
		PreparedStatement getCompanyStatement = null;

		try {

			connection = DataBaseConnector.connect();

			getCompanyStatement = connection.prepareStatement("SELECT * FROM company WHERE id = ?;");

			getCompanyStatement.setLong(1, id);

			getCompanyResult = getCompanyStatement.executeQuery();

			company = CompanyMapper.getCompany(getCompanyResult);

			connection.close();

		} catch (SQLException e) {
			throw new DaoException("Company DAO error in getCompany method " + e.getMessage());
		}

		logger.info("Company retrieved");
		return company;
	}

	@Override
	public Company addCompany(Company company) {

		ResultSet addCompanyResult = null;
		Connection connection = null;
		PreparedStatement addCompanyStatement = null;

		try {

			connection = DataBaseConnector.connect();

			addCompanyStatement = connection.prepareStatement("INSERT INTO company(name) VALUES (?);",
					Statement.RETURN_GENERATED_KEYS);

			if (company != null && StringUtils.isNotBlank(company.getName())) {
				addCompanyStatement.setString(1, company.getName());
			} else {
				throw new DaoException("Company DAO error in addCompany method, name is mandatory");
			}

			addCompanyStatement.executeUpdate();

			addCompanyResult = addCompanyStatement.getGeneratedKeys();

			if (addCompanyResult.next()) {
				company.setId(addCompanyResult.getLong(1));
			}

			connection.close();

		} catch (SQLException e) {
			throw new DaoException("Company DAO error in addCompany method " + e.getMessage());
		}

		logger.info("Company successfully added");
		return company;
	}

	@Override
	public List<Company> listCompanies() {

		List<Company> companiesList = new ArrayList<Company>();

		ResultSet listCompaniesResult = null;
		Connection connection = null;
		Statement listCompaniesStatement = null;

		try {

			connection = DataBaseConnector.connect();
			listCompaniesStatement = connection.createStatement();

			listCompaniesResult = listCompaniesStatement.executeQuery("SELECT * FROM company ORDER BY name;");

			companiesList = CompanyMapper.getCompanies(listCompaniesResult);

			connection.close();

		} catch (SQLException e) {
			throw new DaoException("Company DAO error in listCompanies method " + e.getMessage());
		}

		logger.info("Companies list retrieved");

		return companiesList;
	}

	@Override
	public boolean removeCompany(long id) {

		ComputerDaoImpl computerDaoImpl = ComputerDaoImpl.INSTANCE;

		List<Computer> listComputers = computerDaoImpl.listComputers(id);

		Connection connection = null;

		connection = DataBaseConnector.connect();

		for (Computer computer : listComputers) {

			PreparedStatement removeComputerStatement = null;

			try {

				removeComputerStatement = connection.prepareStatement("DELETE FROM computer WHERE id = ?;");

				removeComputerStatement.setLong(1, computer.getId());

				removeComputerStatement.executeUpdate();

			} catch (SQLException e) {
				throw new DaoException("Computer DAO error in removeComputer method " + e.getMessage());

			}

		}

		PreparedStatement removeCompanyStatement = null;

		try {

			removeCompanyStatement = connection.prepareStatement("DELETE FROM company WHERE id = ?;");

			removeCompanyStatement.setLong(1, id);

			removeCompanyStatement.executeUpdate();

			connection.close();

		} catch (SQLException e) {
			throw new DaoException("Company DAO error in removeCompany method " + e.getMessage());

		}

		logger.info("Company and its computers successfully removed");

		return true;
	}

}