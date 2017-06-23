package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public interface ComputerDao {

	public Page<Computer> listComputers(int pageNumber, int pageSize);

	public int countComputers();

	public Computer getComputer(long id);

	public Computer addComputer(Computer computer);

	public boolean updateComputer(Computer computer);

	public boolean removeComputer(long id);

	public String toString();

}