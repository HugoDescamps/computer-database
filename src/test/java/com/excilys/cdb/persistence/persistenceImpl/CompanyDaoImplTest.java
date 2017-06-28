package com.excilys.cdb.persistence.persistenceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoException;

public class CompanyDaoImplTest {

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

		companyDaoImpl.removeCompany(company.getId());

	}

	@Test
	public void testRemoveCompanyNormalBehaviour() {

		ComputerDaoImpl computerDaoImpl = ComputerDaoImpl.INSTANCE;

		Company company = companyDaoImpl.addCompany(new Company(1, "test"));

		computerDaoImpl.addComputer(new Computer(1, "test1", null, null, company));
		computerDaoImpl.addComputer(new Computer(2, "test2", null, null, company));
		computerDaoImpl.addComputer(new Computer(3, "test3", null, null, company));
		computerDaoImpl.addComputer(new Computer(4, "test4", null, null, company));
		computerDaoImpl.addComputer(new Computer(5, "test5", null, null, company));

		assertTrue(companyDaoImpl.removeCompany(company.getId()));

	}

}