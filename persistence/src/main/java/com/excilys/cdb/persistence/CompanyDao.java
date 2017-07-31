package com.excilys.cdb.persistence;

import java.util.List;

import org.hibernate.Session;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Page;

public interface CompanyDao {

	public Page<Company> listCompanies(int pageNumber, int pageSize);

	public List<Company> listCompanies();

	public Company getCompany(long id);

	public Company addCompany(Company company);
	
	public Company updateCompany(Company company);

	public void removeCompany(long id, Session session);

	public String toString();

}