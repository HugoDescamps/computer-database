package com.excilys.cdb.service;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

public interface CompanyService {

	public Page<Company> getCompanies(int pageNumber, int pageSize);

	public Company getCompany(long id);

}