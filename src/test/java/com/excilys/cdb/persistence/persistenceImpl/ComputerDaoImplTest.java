package com.excilys.cdb.persistence.persistenceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoException;
import com.excilys.cdb.service.ServiceException;

public class ComputerDaoImplTest {

	private ComputerDaoImpl computerDaoImpl = ComputerDaoImpl.INSTANCE;

	@Test
	public void testListComputersNormalBehaviour() {

		assertEquals(3, computerDaoImpl.listComputers(3, 5, "", "").getNumber());
		assertEquals(5, computerDaoImpl.listComputers(3, 5, "", "").getSize());
		assertEquals(0, computerDaoImpl.listComputers(1, 0, "", "").getObjectsList().size());

	}

	@Test
	public void testGetComputerNormalBehaviour() {

		assertNotNull(computerDaoImpl.getComputer(1));

	}

	@Test(expected = DaoException.class)
	public void testAddComputerIncorrectInput() {

		Computer computer = new Computer();

		computerDaoImpl.addComputer(computer);

	}

	@Test
	public void testAddComputerNormalBehaviour() {

		Computer computer = computerDaoImpl.addComputer(new Computer(1, "test", null, null, null));

		assertNotNull(computer);

		computerDaoImpl.removeComputer(computer.getId());

	}

	@Test
	public void testUpdateComputer() {

		Computer computer = new Computer(1, "test", null, null, null);
		computer = computerDaoImpl.addComputer(computer);

		computer.setName("updateTest");
		computer.setIntroduced(LocalDate.parse("2012-09-01"));
		computer.setDiscontinued(LocalDate.parse("2012-09-02"));
		computer.setCompany(new Company(2, null));

		computerDaoImpl.updateComputer(computer);

		computerDaoImpl.removeComputer(computer.getId());
	}

	@Test
	public void testRemoveComputer() {

		computerDaoImpl.removeComputer(computerDaoImpl.addComputer(new Computer(1, "test", null, null, null)).getId());

	}

	@Test
	public void testRemoveComputersNormalBehaviour() {

		CompanyDaoImpl companyDaoImpl = CompanyDaoImpl.INSTANCE;

		Company company = companyDaoImpl.addCompany(new Company(1, "test"));

		computerDaoImpl.addComputer(new Computer(1, "test1", null, null, company));
		computerDaoImpl.addComputer(new Computer(2, "test2", null, null, company));

		Connection connection = DataBaseConnector.connect();

		computerDaoImpl.removeComputers(connection, company.getId());
		companyDaoImpl.removeCompany(connection, company.getId());

		try {
			connection.close();
		} catch (SQLException e) {
			throw new ServiceException(
					"ComputerDaoImplTest error in testRemoveComputersNormalBehaviour method, could not close connection "
							+ e.getMessage());
		}
	}

}
