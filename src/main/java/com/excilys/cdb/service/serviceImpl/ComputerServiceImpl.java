package com.excilys.cdb.service.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
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
	public Page<Computer> getComputers(int pageNumber, int pageSize) {
		return computerDaoImpl.listComputers(pageNumber, pageSize);
	}

	@Override
	public int countComputers() {
		
		return computerDaoImpl.countComputers();
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
	public boolean removeComputer(long id) {
		return computerDaoImpl.removeComputer(id);
	}

}
