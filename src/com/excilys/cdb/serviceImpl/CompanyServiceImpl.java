package com.excilys.cdb.serviceImpl;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistenceImpl.CompanyDaoImpl;
import com.excilys.cdb.service.CompanyService;

public class CompanyServiceImpl implements CompanyService {
	
	private CompanyDaoImpl companyDaoImpl;
	
	public CompanyServiceImpl() {
		companyDaoImpl = new CompanyDaoImpl();
	}

	@Override
	public List<Company> getCompanies() {
		return companyDaoImpl.listCompanies();
	}

}
