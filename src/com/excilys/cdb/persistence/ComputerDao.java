package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Computer;

public interface ComputerDao {

	public List<Computer> listComputers();

	public Computer getComputer(long id);

	public Computer addComputer(Computer computer);

	public boolean updateComputer(Computer computer);

	public boolean removeComputer(long id);
}