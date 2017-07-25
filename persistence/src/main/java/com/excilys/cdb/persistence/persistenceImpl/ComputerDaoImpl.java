package com.excilys.cdb.persistence.persistenceImpl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.persistence.ComputerDao;
import com.excilys.cdb.persistence.DaoException;
import com.excilys.cdb.persistence.config.HibernateConfig;

@Repository("computerDao")
public class ComputerDaoImpl implements ComputerDao {

	private static final Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

	private ComputerDaoImpl() {
	}

	@Override
	public Page<Computer> listComputers(int pageNumber, int pageSize, String search, OrderColumn column, OrderWay way) {

		Page<Computer> computersPage = new Page<Computer>();

		String requestColumn = "";

		switch (column) {
		case COMPUTER:
			requestColumn = "c.name";
			break;
		case COMPANY:
			requestColumn = "company.name";
			break;
		default:
			requestColumn = "c.id";
			break;
		}

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {

			String stringQuery = "SELECT c FROM Computer AS c LEFT JOIN c.company AS company WHERE c.name LIKE :search OR company.name LIKE :search";

			switch (way) {
			case DESC:
				stringQuery += " ORDER BY " + requestColumn + " DESC";
				break;
			default:
				stringQuery += " ORDER BY " + requestColumn + " ASC";
				break;
			}

			Query<Computer> computersListQuery = session.createQuery(stringQuery, Computer.class);

			computersListQuery.setParameter("search", "%" + search + "%");
			computersListQuery.setFirstResult((pageNumber - 1) * pageSize);
			computersListQuery.setMaxResults(pageSize - 1);

			computersPage.setObjectsList(computersListQuery.list());
			computersPage.setSize(pageSize);
			computersPage.setNumber(pageNumber);
		}

		logger.info("Computers list retrieved");
		return computersPage;
	}

	@Override
	public int countComputers(String search) {

		int result = 0;

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {

			Query<Long> countComputersQuery = session.createQuery(
					"Select COUNT(c) From Computer AS c LEFT JOIN c.company AS company WHERE c.name LIKE :search OR company.name LIKE :search",
					Long.class);
			countComputersQuery.setParameter("search", "%" + search + "%");

			result = countComputersQuery.uniqueResult().intValue();
		}

		logger.info("Computers count retrieved");
		return result;
	}

	@Override
	public Computer getComputer(long id) {

		Computer computer = new Computer();

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {
			Query<Computer> computerQuery = session.createQuery(
					"SELECT c FROM Computer AS c LEFT JOIN c.company AS company WHERE c.id = :id", Computer.class);
			computerQuery.setParameter("id", id);

			computer = computerQuery.uniqueResult();
		}

		logger.info("Computer retrieved");
		return computer;
	}

	@Override
	public Computer addComputer(Computer computer) {

		if (computer == null || StringUtils.isBlank(computer.getName())) {
			throw new DaoException("Computer DAO error in addComputer method, name is mandatory");
		} else {
			try (Session session = HibernateConfig.getSessionFactory().openSession()) {
				computer.setId((long) session.save(computer));
			}
		}

		logger.info("Computer successfully added");
		return computer;

	}

	@Override
	public boolean updateComputer(Computer computer) {

		if (computer == null || StringUtils.isBlank(computer.getName())) {
			throw new DaoException("Computer DAO error in addComputer method, name is mandatory");
		} else {
			Long company_id = null;

			if (computer.getCompany() != null) {
				company_id = computer.getCompany().getId();
			}

			try (Session session = HibernateConfig.getSessionFactory().openSession()) {

				Query<?> query = session.createQuery(
						"UPDATE Computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id WHERE id = :id");

				query.setParameter("name", computer.getName());
				query.setParameter("introduced", computer.getIntroduced());
				query.setParameter("discontinued", computer.getDiscontinued());
				query.setParameter("company_id", company_id);
				query.setParameter("id", computer.getId());

				session.beginTransaction();
				query.executeUpdate();
				session.getTransaction().commit();
			}
		}

		logger.info("Computer successfully updated");
		return true;
	}

	@Override
	public void removeComputer(long id) {

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {

			Query<?> removeComputerQuery = session.createQuery("DELETE FROM Computer WHERE id = :id");
			removeComputerQuery.setParameter("id", id);

			session.beginTransaction();
			removeComputerQuery.executeUpdate();
			session.getTransaction().commit();
		}

		logger.info("Computer successfully removed");
	}

	@Override
	public void removeComputers(long company_id, Session session) {

		Query<?> removeComputerQuery = session.createQuery("DELETE FROM Computer WHERE company_id = :company_id");
		removeComputerQuery.setParameter("company_id", company_id);

		removeComputerQuery.executeUpdate();

		throw new DaoException("marche po");

		// logger.info("Company's computers removed");
	}

}
