package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public interface ComputerService {

	public Page<Computer> getComputers(int pageNumber, int pageSize);
	
	public List<Computer> getComputers(long company_id);
	
	public int countComputers();

	public Computer getComputer(long id);

	public Computer addComputer(Computer computer);

	public boolean updateComputer(Computer computer);

	public boolean removeComputer(long id);
	
	
}
