package com.excilys.cdb.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.CompanyDao;
import com.excilys.cdb.persistence.ComputerDao;
import com.excilys.cdb.service.CompanyService;
import com.zaxxer.hikari.HikariDataSource;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	HikariDataSource dataBaseConnection;

	@Autowired
	CompanyDao companyDao;
	@Autowired
	ComputerDao computerDao;

	public CompanyServiceImpl() {
	}

	@Override
	public Page<Company> getCompanies(int pageNumber, int pageSize) {
		return companyDao.listCompanies(pageNumber, pageSize);
	}

	@Override
	public Company getCompany(long id) {
		return companyDao.getCompany(id);
	}

	@Override
	public List<Company> getCompanies() {
		return companyDao.listCompanies();
	}

	@Override
	public Company addCompany(Company company) {
		return companyDao.addCompany(company);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeCompany(long id) {
		
		computerDao.removeComputers(id);
		companyDao.removeCompany(id);
	}

}
