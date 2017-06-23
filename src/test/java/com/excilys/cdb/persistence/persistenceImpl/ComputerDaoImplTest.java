package com.excilys.cdb.persistence.persistenceImpl;

import java.time.LocalDate;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.MyException;

import junit.framework.TestCase;

public class ComputerDaoImplTest extends TestCase {

	private ComputerDaoImpl computerDaoImpl = ComputerDaoImpl.INSTANCE;

	public void testListComputers() {

		assertEquals(3, computerDaoImpl.listComputers(3, 5).getNumber());
		assertEquals(5, computerDaoImpl.listComputers(3, 5).getSize());
		assertEquals(0, computerDaoImpl.listComputers(1, 0).getObjectsList().size());

	}

	public void testGetComputer() {

		try {
			computerDaoImpl.getComputer(0);
			fail("0 id argument exception uncaught");
		} catch (MyException e) {
			// Exception caught
		}

		assertNotNull(computerDaoImpl.getComputer(1));

	}

	public void testAddComputer() {
		Computer computer = null;

		try {
			computerDaoImpl.addComputer(computer);
			fail("Null computer argument exception uncaught");
		} catch (MyException e) {
			// Exception caught
		}
		
		Computer computer2 = computerDaoImpl.addComputer(new Computer(1, "test", null, null, null));

		assertNotNull(computer2);
		
		computerDaoImpl.removeComputer(computer2.getId());

	}

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

	public void testRemoveComputer() {

		assertTrue(computerDaoImpl
				.removeComputer(computerDaoImpl.addComputer(new Computer(1, "test", null, null, null)).getId()));

	}

}
