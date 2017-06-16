package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Computer;

public interface ComputerService {

	public List<Computer> listComputers();
	
	public Computer getComputer(long id);
}
