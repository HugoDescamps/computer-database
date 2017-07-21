package com.excilys.cdb.persistence;

import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Page;

public interface ComputerDao {

	public enum OrderColumn {
		COMPUTER, COMPANY, NULL;
	}

	public enum OrderWay {
		ASC, DESC;
	}

	public Page<Computer> listComputers(int pageNumber, int pageSize, String search, OrderColumn column, OrderWay way);

	public int countComputers(String search);

	public Computer getComputer(long id);

	public Computer addComputer(Computer computer);

	public boolean updateComputer(Computer computer);

	public void removeComputer(long id);

	public void removeComputers(long company_id);

	public String toString();
}
