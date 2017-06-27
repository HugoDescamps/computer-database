package com.excilys.cdb.persistence.persistenceImpl;

import static org.junit.Assert.*;

import org.junit.Test;

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

}