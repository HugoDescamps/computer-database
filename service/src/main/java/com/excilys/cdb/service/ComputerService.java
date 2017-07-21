package com.excilys.cdb.service;

import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.persistence.ComputerDao.OrderColumn;
import com.excilys.cdb.persistence.ComputerDao.OrderWay;

public interface ComputerService {

	public Page<Computer> getComputers(int pageNumber, int pageSize, String search, OrderColumn column, OrderWay way);

	public int countComputers(String search);

	public Computer getComputer(long id);

	public Computer addComputer(Computer computer);

	public boolean updateComputer(Computer computer);

	public void removeComputer(long id);

	public void removeComputers(long company_id);
}
