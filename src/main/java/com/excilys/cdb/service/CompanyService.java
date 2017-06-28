package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

public interface CompanyService {

	public Page<Company> getCompanies(int pageNumber, int pageSize);
	
	public List<Company> getCompanies();

	public Company getCompany(long id);
	
	public Company addCompany(Company company);
	
	public boolean removeCompany(long id);

}