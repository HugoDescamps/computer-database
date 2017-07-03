package com.excilys.cdb.persistence.persistenceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DaoException;
import com.excilys.cdb.service.ServiceException;

public class CompanyDaoImplTest {

	private DataBaseConnector dataBaseConnection = DataBaseConnector.INSTANCE;
	private CompanyDaoImpl companyDaoImpl = CompanyDaoImpl.INSTANCE;

	@Test
	public void testListCompaniesNormalBehaviour() {

		assertEquals(3, companyDaoImpl.listCompanies(3, 5).getNumber());
		assertEquals(5, companyDaoImpl.listCompanies(3, 5).getSize());
		assertEquals(0, companyDaoImpl.listCompanies(1, 0).getObjectsList().size());

	}

	@Test
	public void testGetCompaniesNormalBehaviour() {

		assertNotNull(companyDaoImpl.getCompany(1));
	}

	@Test(expected = DaoException.class)
	public void testAddCompanyIncorrectInput() {

		Company company = new Company();

		companyDaoImpl.addCompany(company);

	}

	@Test
	public void testAddCompanyNormalBehaviour() {

		Company company = companyDaoImpl.addCompany(new Company(1, "test"));

		assertNotNull(company);

		Connection connection = dataBaseConnection.connect();

		companyDaoImpl.removeCompany(connection, company.getId());

		try {
			connection.close();
		} catch (SQLException e) {
			throw new ServiceException(
					"CompanyDaoImplTest error in testAddCompanyNormalBehaviour method, could not close connection "
							+ e.getMessage());
		}

	}

	@Test
	public void testRemoveCompanyNormalBehaviour() {

		Company company = companyDaoImpl.addCompany(new Company(1, "test"));

		Connection connection = dataBaseConnection.connect();

		companyDaoImpl.removeCompany(connection, company.getId());

		try {
			connection.close();
		} catch (SQLException e) {
			throw new ServiceException(
					"CompanyDaoImplTest error in testRemoveCompanyNormalBehaviour method, could not close connection "
							+ e.getMessage());
		}

	}

}