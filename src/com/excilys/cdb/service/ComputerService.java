package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Computer;

public interface ComputerService {

	public List<Computer> getComputers();

	public Computer getComputer(long id);
	
	public Computer addComputer(Computer computer);

	public boolean updateComputer(Computer computer);

	public boolean removeComputer(long id);
}
