package com.excilys.cdb.service.serviceImpl;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ComputerDao.OrderColumn;
import com.excilys.cdb.persistence.ComputerDao.OrderWay;
import com.excilys.cdb.persistence.persistenceImpl.ComputerDaoImpl;
import com.excilys.cdb.service.ComputerService;

public enum ComputerServiceImpl implements ComputerService {
	INSTANCE;

	private ComputerDaoImpl computerDaoImpl;

	static final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);

	private ComputerServiceImpl() {
		computerDaoImpl = ComputerDaoImpl.INSTANCE;
	}

	@Override
	public Page<Computer> getComputers(int pageNumber, int pageSize, String search, OrderColumn column, OrderWay way) {
		return computerDaoImpl.listComputers(pageNumber, pageSize, search, column, way);

	@Override
	public int countComputers(String search) {

		return computerDaoImpl.countComputers(search);
	}

	@Override
	public Computer getComputer(long id) {
		return computerDaoImpl.getComputer(id);
	}

	@Override
	public Computer addComputer(Computer computer) {
		return computerDaoImpl.addComputer(computer);
	}

	@Override
	public boolean updateComputer(Computer computer) {
		return computerDaoImpl.updateComputer(computer);
	}

	@Override
	public void removeComputer(long id) {
		computerDaoImpl.removeComputer(id);
	}

	@Override
	public void removeComputers(Connection connection, long company_id) {
		computerDaoImpl.removeComputers(connection, company_id);
	}

}
