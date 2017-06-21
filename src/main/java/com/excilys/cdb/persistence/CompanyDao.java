package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

public interface CompanyDao {

	public Page<Company> listCompanies(int pageNumber, int pageSize);

	public Company getCompany(long id);

	public String toString();

}