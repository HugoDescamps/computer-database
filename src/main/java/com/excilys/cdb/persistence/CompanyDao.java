package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

public interface CompanyDao {

	public Page<Company> listCompanies(int pageNumber, int pageSize);

	public List<Company> listCompanies();

	public Company getCompany(long id);

	public Company addCompany(Company company);
	
	public boolean removeCompany(long id);

	public String toString();

}