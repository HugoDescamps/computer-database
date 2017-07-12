package com.excilys.cdb.persistence.persistenceImpl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb.persistence.CompanyDao;

public class CompanyDaoImplTest {

	@Autowired
	CompanyDao companyDao;

	@Test
	public void testListCompaniesNormalBehaviour() {

		assertEquals(3, companyDao.listCompanies(3, 5).getNumber());
		assertEquals(5, companyDao.listCompanies(3, 5).getSize());
		assertEquals(0, companyDao.listCompanies(1, 0).getObjectsList().size());

	}

	/*
	 * @Test public void testGetCompaniesNormalBehaviour() {
	 * 
	 * assertNotNull(companyDao.getCompany(1)); }
	 * 
	 * @Test(expected = DaoException.class) public void
	 * testAddCompanyIncorrectInput() {
	 * 
	 * Company company = new Company();
	 * 
	 * companyDao.addCompany(company);
	 * 
	 * }
	 * 
	 * @Test public void testAddCompanyNormalBehaviour() {
	 * 
	 * Company company = companyDao.addCompany(new Company(1, "test"));
	 * 
	 * assertNotNull(company);
	 * 
	 * companyDao.removeCompany(company.getId());
	 * 
	 * }
	 * 
	 * @Test public void testRemoveCompanyNormalBehaviour() {
	 * 
	 * Company company = companyDao.addCompany(new Company(1, "test"));
	 * 
	 * companyDao.removeCompany(company.getId());
	 * 
	 * }
	 */

}