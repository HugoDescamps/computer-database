package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Page;


public interface CompanyService {

	public Page<Company> getCompanies(int pageNumber, int pageSize);

	public List<Company> getCompanies();

	public Company getCompany(long id);

	public Company addCompany(Company company);
	
	public Company updateCompany(Company company);

	public void removeCompany(long id);

}