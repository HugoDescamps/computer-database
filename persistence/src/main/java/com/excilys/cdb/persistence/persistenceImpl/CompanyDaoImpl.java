package com.excilys.cdb.persistence.persistenceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.persistence.CompanyDao;
import com.excilys.cdb.persistence.DaoException;
import com.excilys.cdb.persistence.config.HibernateConfig;

@Repository("companyDao")
public class CompanyDaoImpl implements CompanyDao {

	private static final Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

	private CompanyDaoImpl() {
	}

	@Override
	public Page<Company> listCompanies(int pageNumber, int pageSize) {

		Page<Company> companiesPage = new Page<Company>();

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {

			Query<Company> companiesListQuery = session.createQuery("FROM Company", Company.class);
			companiesListQuery.setFirstResult((pageNumber - 1) * pageSize);
			companiesListQuery.setMaxResults(pageSize - 1);

			companiesPage.setObjectsList(companiesListQuery.list());
			companiesPage.setSize(pageSize);
			companiesPage.setNumber(pageNumber);
		}
		
		logger.info("Companies list retrieved");
		return companiesPage;
	}

	public Company getCompany(long id) {

		Company company = new Company();

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {
			Query<Company> companyQuery = session.createQuery("FROM Company WHERE id = :id", Company.class);
			companyQuery.setParameter("id", id);

			company = companyQuery.uniqueResult();
		}

		logger.info("Company retrieved");
		return company;
	}

	@Override
	public Company addCompany(Company company) {

		if (company == null || StringUtils.isBlank(company.getName())) {
			throw new DaoException("Company DAO error in addCompany method, name is mandatory");
		} else {
			try (Session session = HibernateConfig.getSessionFactory().openSession()) {
				company.setId((long) session.save(company));
			}
		}

		logger.info("Company successfully added");
		return company;
	}

	@Override
	public List<Company> listCompanies() {

		List<Company> companiesList = new ArrayList<Company>();

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {

			Query<Company> companiesListQuery = session.createQuery("FROM Company ORDER BY name", Company.class);

			companiesList = companiesListQuery.list();
		}

		logger.info("Companies list retrieved");

		return companiesList;
	}

	@Override
	public void removeCompany(long id) {

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {

			Query<?> removeCompanyQuery = session.createQuery("DELETE FROM Company WHERE id = :id");
			removeCompanyQuery.setParameter("id", id);

			session.beginTransaction();
			removeCompanyQuery.executeUpdate();
			session.getTransaction().commit();
		}

		logger.info("Company successfully removed");
	}

}