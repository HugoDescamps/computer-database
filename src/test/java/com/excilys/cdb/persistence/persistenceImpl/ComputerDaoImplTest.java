package com.excilys.cdb.persistence.persistenceImpl;

import java.time.LocalDate;

import org.junit.Test;
import static org.junit.Assert.*;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoException;

public class ComputerDaoImplTest {

	private ComputerDaoImpl computerDaoImpl = ComputerDaoImpl.INSTANCE;

	@Test
	public void testListComputersNormalBehaviour() {

		assertEquals(3, computerDaoImpl.listComputers(3, 5).getNumber());
		assertEquals(5, computerDaoImpl.listComputers(3, 5).getSize());
		assertEquals(0, computerDaoImpl.listComputers(1, 0).getObjectsList().size());

	}
	
	@Test
	public void testListComputersCompanyNormalBehaviour() {

		assertNotEquals(0, computerDaoImpl.listComputers(1).size());
		assertEquals(0, computerDaoImpl.listComputers(-20).size());
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

		assertTrue(computerDaoImpl
				.removeComputer(computerDaoImpl.addComputer(new Computer(1, "test", null, null, null)).getId()));

	}

}
