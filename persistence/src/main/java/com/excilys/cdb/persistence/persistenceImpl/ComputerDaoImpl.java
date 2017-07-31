package com.excilys.cdb.persistence.persistenceImpl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.OrderColumnEnum;
import com.excilys.cdb.core.OrderWayEnum;
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
	public Page<Computer> listComputers(int pageNumber, int pageSize, String search, OrderColumnEnum column,
			OrderWayEnum way) {

		Page<Computer> computersPage = new Page<Computer>();

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {

			String listComputersQuery = "SELECT c FROM Computer AS c LEFT JOIN c.company AS company WHERE c.name LIKE :search OR company.name LIKE :search ORDER BY "
					+ column.toString() + " " + way.toString();

			Query<Computer> computersListQuery = session.createQuery(listComputersQuery, Computer.class);

			computersListQuery.setParameter("search", "%" + search + "%");
			computersListQuery.setFirstResult((pageNumber - 1) * pageSize);
			computersListQuery.setMaxResults(pageSize);

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
			computer = session.get(Computer.class, id);
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

			try (Session session = HibernateConfig.getSessionFactory().openSession()) {
				session.beginTransaction();
				session.update(computer);
				session.getTransaction().commit();
			}
		}

		logger.info("Computer successfully updated");
		return true;
	}

	@Override
	public void removeComputer(long id) {

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {
			session.beginTransaction();
			session.remove(session.get(Computer.class, id));
			session.getTransaction().commit();
		}

		logger.info("Computer successfully removed");
	}

	@Override
	public void removeComputers(long company_id, Session session) {

		Query<?> removeComputerQuery = session.createQuery("DELETE FROM Computer WHERE company_id = :company_id");
		removeComputerQuery.setParameter("company_id", company_id);

		removeComputerQuery.executeUpdate();

		logger.info("Company's computers removed");
	}

}
