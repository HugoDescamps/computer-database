package com.excilys.cdb.service.serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.DaoException;
import com.excilys.cdb.persistence.persistenceImpl.CompanyDaoImpl;
import com.excilys.cdb.persistence.persistenceImpl.ComputerDaoImpl;
import com.excilys.cdb.persistence.persistenceImpl.DataBaseConnector;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ServiceException;

public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;

	private DataBaseConnector dataBaseConnection;
	private CompanyDaoImpl companyDaoImpl;

	private CompanyServiceImpl() {
		dataBaseConnection = DataBaseConnector.INSTANCE;
		companyDaoImpl = CompanyDaoImpl.INSTANCE;
	}

	@Override
	public Page<Company> getCompanies(int pageNumber, int pageSize) {
		return companyDaoImpl.listCompanies(pageNumber, pageSize);
	}

	@Override
	public Company getCompany(long id) {
		return companyDaoImpl.getCompany(id);
	}

	@Override
	public List<Company> getCompanies() {
		return companyDaoImpl.listCompanies();
	}

	@Override
	public Company addCompany(Company company) {
		return companyDaoImpl.addCompany(company);
	}

	@Override
	public void removeCompany(long id) {

		ComputerDaoImpl computerDaoImpl = ComputerDaoImpl.INSTANCE;

		Connection connection = dataBaseConnection.connect();

		try {
			connection.setAutoCommit(false);

			computerDaoImpl.removeComputers(connection, id);
			companyDaoImpl.removeCompany(connection, id);
			connection.commit();

		} catch (SQLException e1) {

			try {
				connection.rollback();
			} catch (SQLException e2) {
				throw new ServiceException(
						"CompanyService error in removeCompany method, could not execute rollback " + e2.getMessage());
			}
			throw new DaoException("CompanyService error in removeCompany method " + e1.getMessage());

		} finally {
			dataBaseConnection.close();
		}

	}

}
