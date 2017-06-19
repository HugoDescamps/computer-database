package com.excilys.cdb.service.serviceImpl;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.persistenceImpl.CompanyDaoImpl;
import com.excilys.cdb.service.CompanyService;

public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;
	
	private CompanyDaoImpl companyDaoImpl;
	
	private CompanyServiceImpl() {
		companyDaoImpl = CompanyDaoImpl.INSTANCE;
	}

	@Override
	public List<Company> getCompanies() {
		return companyDaoImpl.listCompanies();
	}

}
