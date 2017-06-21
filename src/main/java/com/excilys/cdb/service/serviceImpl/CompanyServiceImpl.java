package com.excilys.cdb.service.serviceImpl;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.persistenceImpl.CompanyDaoImpl;
import com.excilys.cdb.service.CompanyService;

public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;

	private CompanyDaoImpl companyDaoImpl;

	private CompanyServiceImpl() {
		companyDaoImpl = CompanyDaoImpl.INSTANCE;
	}

	@Override
	public Page<Company> getCompanies(int pageNumber, int pageSize) {
		return companyDaoImpl.listCompanies(pageNumber, pageSize);
	}

	@Override
	public Company getCompany(long id) {
		return companyDaoImpl.getCompany(id);
	}

}
