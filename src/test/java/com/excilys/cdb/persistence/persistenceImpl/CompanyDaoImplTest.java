package com.excilys.cdb.persistence.persistenceImpl;

import com.excilys.cdb.persistence.MyException;

import junit.framework.TestCase;

public class CompanyDaoImplTest extends TestCase {

	private CompanyDaoImpl companyDaoImpl = CompanyDaoImpl.INSTANCE;

	public void testListCompanies() {

		assertEquals(3, companyDaoImpl.listCompanies(3, 5).getNumber());
		assertEquals(5, companyDaoImpl.listCompanies(3, 5).getSize());
		assertEquals(0, companyDaoImpl.listCompanies(1, 0).getObjectsList().size());

	}

	public void testGetCompanies() {

		try {
			companyDaoImpl.getCompany(0);
			fail("0 argument exception uncaught");
		} catch (MyException e) {
			// Exception caught
		}

		assertNotNull(companyDaoImpl.getCompany(1));

	}

}