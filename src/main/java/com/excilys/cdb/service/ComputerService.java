package com.excilys.cdb.service;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public interface ComputerService {

	public Page<Computer> getComputers(int pageNumber, int pageSize);

	public Computer getComputer(long id);

	public Computer addComputer(Computer computer);

	public boolean updateComputer(Computer computer);

	public boolean removeComputer(long id);
	
	public int countComputers();
}
