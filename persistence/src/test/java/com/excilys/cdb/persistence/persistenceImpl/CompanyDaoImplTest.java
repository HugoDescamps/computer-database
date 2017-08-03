package com.excilys.cdb.persistence.persistenceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.persistence.CompanyDao;
import com.excilys.cdb.persistence.DaoException;
import com.excilys.cdb.persistence.config.HibernateConfig;
import com.excilys.cdb.persistence.config.PersistenceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })

public class CompanyDaoImplTest {

	@Autowired
	CompanyDao companyDao;

	@Test
	public void testListCompaniesNormalBehaviourNumber() {
		assertEquals(3, companyDao.listCompanies(3, 5).getNumber());
	}

	@Test
	public void testListCompaniesNormalBehaviourSize() {
		assertEquals(5, companyDao.listCompanies(3, 5).getSize());
	}

	@Test
	public void testGetCompaniesNormalBehaviour() {
		assertNotNull(companyDao.getCompany(1));
	}

	@Test(expected = DaoException.class)
	public void testAddCompanyIncorrectInput() {
		companyDao.addCompany(new Company());
	}

	@Test
	public void testAddCompanyNormalBehaviour() {

		Company company = companyDao.addCompany(new Company(1, "test"));

		assertNotNull(company);

		Session session = HibernateConfig.getSessionFactory().openSession();

		session.beginTransaction();
		companyDao.removeCompany(company.getId(), session);

		session.getTransaction().commit();

	}

	@Test
	public void testUpdateComputer() {

		Company company = new Company(1, "test");
		company = companyDao.addCompany(company);

		company.setName("updateTest");

		companyDao.updateCompany(company);

		Session session = HibernateConfig.getSessionFactory().openSession();

		session.beginTransaction();

		companyDao.removeCompany(company.getId(), session);

		session.getTransaction().commit();

	}

	@Test
	public void testRemoveCompanyNormalBehaviour() {

		Company company = companyDao.addCompany(new Company(1, "test"));

		Session session = HibernateConfig.getSessionFactory().openSession();

		session.beginTransaction();

		companyDao.removeCompany(company.getId(), session);

		session.getTransaction().commit();
	}

}