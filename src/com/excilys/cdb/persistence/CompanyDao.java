package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Company;

public interface CompanyDao {
	
	public List<Company> listCompanies();

}