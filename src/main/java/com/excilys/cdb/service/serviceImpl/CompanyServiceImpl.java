package com.excilys.cdb.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.persistenceImpl.CompanyDaoImpl;
import com.excilys.cdb.persistence.persistenceImpl.ComputerDaoImpl;
import com.excilys.cdb.service.CompanyService;
import com.zaxxer.hikari.HikariDataSource;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	HikariDataSource dataBaseConnection;

	@Autowired
	CompanyDaoImpl companyDaoImpl;
	@Autowired
	ComputerDaoImpl computerDaoImpl;

	public CompanyServiceImpl() {
	}

	@Override
	public Page<Company> getCompanies(int pageNumber, int pageSize) {
		return companyDaoImpl.listCompanies(pageNumber, pageSize);
	}

	@Override
	public Company getCompany(long id) {
		return companyDaoImpl.getCompany(id);
	}

	@Override
	public List<Company> getCompanies() {
		return companyDaoImpl.listCompanies();
	}

	@Override
	public Company addCompany(Company company) {
		return companyDaoImpl.addCompany(company);
	}

	@Override
	public void removeCompany(long id) {
		computerDaoImpl.removeComputers(id);
		companyDaoImpl.removeCompany(id);
	}

}
