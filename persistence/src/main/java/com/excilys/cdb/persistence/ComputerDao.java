package com.excilys.cdb.persistence;

import org.hibernate.Session;

import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.OrderColumnEnum;
import com.excilys.cdb.core.OrderWayEnum;
import com.excilys.cdb.core.Page;

public interface ComputerDao {

	public Page<Computer> listComputers(int pageNumber, int pageSize, String search, OrderColumnEnum column, OrderWayEnum way);

	public int countComputers(String search);

	public Computer getComputer(long id);

	public Computer addComputer(Computer computer);

	public boolean updateComputer(Computer computer);

	public void removeComputer(long id);

	public void removeComputers(long company_id, Session session);

	public String toString();
}
