package com.excilys.cdb.persistence;

import java.sql.Connection;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public interface ComputerDao {

	public Page<Computer> listComputers(int pageNumber, int pageSize, String search);

	public int countComputers(String search);

	public Computer getComputer(long id);

	public Computer addComputer(Computer computer);

	public boolean updateComputer(Computer computer);

	public void removeComputer(long id);

	public void removeComputers(Connection connection, long company_id);

	public String toString();

}