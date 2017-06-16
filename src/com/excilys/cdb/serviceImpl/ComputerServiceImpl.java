package com.excilys.cdb.serviceImpl;

import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistenceImpl.ComputerDaoImpl;
import com.excilys.cdb.service.ComputerService;

public class ComputerServiceImpl implements ComputerService {
	
	private ComputerDaoImpl computerDaoImpl;
	
	public ComputerServiceImpl() {
		computerDaoImpl = new ComputerDaoImpl();
	}

	@Override
	public List<Computer> listComputers() {
		return computerDaoImpl.listComputers();
	}

	@Override
	public Computer getComputer(long id) {
		return computerDaoImpl.getComputer(id);
	}

}
