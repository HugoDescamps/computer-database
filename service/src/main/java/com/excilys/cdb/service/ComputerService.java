package com.excilys.cdb.service;

import org.hibernate.Session;

import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.OrderColumnEnum;
import com.excilys.cdb.core.OrderWayEnum;
import com.excilys.cdb.core.Page;

public interface ComputerService {

	public Page<Computer> getComputers(int pageNumber, int pageSize, String search, OrderColumnEnum column,
			OrderWayEnum way);

	public int countComputers(String search);

	public Computer getComputer(long id);

	public Computer addComputer(Computer computer);

	public boolean updateComputer(Computer computer);

	public void removeComputer(long id);

	public void removeComputers(long company_id, Session session);
}
