package com.excilys.cdb.service.serviceImpl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.OrderColumnEnum;
import com.excilys.cdb.core.OrderWayEnum;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.persistence.ComputerDao;
import com.excilys.cdb.service.ComputerService;

@Service("computerService")
public class ComputerServiceImpl implements ComputerService {

	@Autowired
	ComputerDao computerDao;

	public ComputerServiceImpl() {
	}

	@Override
	public Page<Computer> getComputers(int pageNumber, int pageSize, String search, OrderColumnEnum column, OrderWayEnum way) {
		return computerDao.listComputers(pageNumber, pageSize, search, column, way);
	}

	@Override
	public int countComputers(String search) {

		return computerDao.countComputers(search);
	}

	@Override
	public Computer getComputer(long id) {
		return computerDao.getComputer(id);
	}

	@Override
	public Computer addComputer(Computer computer) {
		return computerDao.addComputer(computer);
	}

	@Override
	public boolean updateComputer(Computer computer) {
		return computerDao.updateComputer(computer);
	}

	@Override
	public void removeComputer(long id) {
		computerDao.removeComputer(id);
	}

	@Override
	public void removeComputers(long company_id, Session session) {
		computerDao.removeComputers(company_id, session);
	}

}
