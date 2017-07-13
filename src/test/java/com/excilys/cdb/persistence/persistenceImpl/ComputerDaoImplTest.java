package com.excilys.cdb.persistence.persistenceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.excilys.cdb.config.CLIConfig;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanyDao;
import com.excilys.cdb.persistence.ComputerDao;
import com.excilys.cdb.persistence.ComputerDao.OrderColumn;
import com.excilys.cdb.persistence.ComputerDao.OrderWay;
import com.excilys.cdb.persistence.DaoException;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CLIConfig.class })

public class ComputerDaoImplTest {

	@Autowired
	ComputerDao computerDao;

	@Autowired
	CompanyDao companyDao;

	@Test
	public void testListComputersNormalBehaviour() {

		System.out.println(computerDao.countComputers("") + " ordinateurs listés");

		assertEquals(3, computerDao.listComputers(3, 5, "", OrderColumn.NULL, OrderWay.ASC).getNumber());
		assertEquals(5, computerDao.listComputers(3, 5, "", OrderColumn.NULL, OrderWay.ASC).getSize());
		assertEquals(0, computerDao.listComputers(1, 0, "", OrderColumn.NULL, OrderWay.ASC).getObjectsList().size());

	}

	@Test
	public void testGetComputerNormalBehaviour() {

		assertNotNull(computerDao.getComputer(1));

	}

	@Test(expected = DaoException.class)
	public void testAddComputerIncorrectInput() {

		Computer computer = new Computer();

		computerDao.addComputer(computer);

	}

	@Test
	public void testAddComputerNormalBehaviour() {

		Computer computer = computerDao.addComputer(new Computer(1, "test", null, null, null));

		assertNotNull(computer);

		computerDao.removeComputer(computer.getId());

	}

	@Test
	public void testUpdateComputer() {

		Computer computer = new Computer(1, "test", null, null, null);
		computer = computerDao.addComputer(computer);

		computer.setName("updateTest");
		computer.setIntroduced(LocalDate.parse("2012-09-01"));
		computer.setDiscontinued(LocalDate.parse("2012-09-02"));
		computer.setCompany(new Company(2, null));

		computerDao.updateComputer(computer);

		computerDao.removeComputer(computer.getId());
	}

	@Test
	public void testRemoveComputer() {

		computerDao.removeComputer(computerDao.addComputer(new Computer(1, "test", null, null, null)).getId());

	}

	@Test
	public void testRemoveComputersNormalBehaviour() {

		Company company = companyDao.addCompany(new Company(1, "test"));

		computerDao.addComputer(new Computer(1, "test1", null, null, company));
		computerDao.addComputer(new Computer(2, "test2", null, null, company));

		computerDao.removeComputers(company.getId());
		companyDao.removeCompany(company.getId());

	}

}
