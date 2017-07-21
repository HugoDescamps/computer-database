package com.excilys.cdb.persistence.persistenceImpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.binding.CompanyMapper;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.persistence.CompanyDao;
import com.excilys.cdb.persistence.DaoException;
import com.zaxxer.hikari.HikariDataSource;

@Repository("companyDao")
public class CompanyDaoImpl implements CompanyDao {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

	private CompanyDaoImpl() {
	}

	@Autowired
	public void setDataSource(HikariDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Page<Company> listCompanies(int pageNumber, int pageSize) {

		List<Company> companiesList = jdbcTemplate.query("SELECT * FROM company LIMIT ?,?;", new CompanyMapper(),
				(pageNumber - 1) * pageSize, pageSize);

		Page<Company> companiesPage = new Page<Company>(companiesList, pageSize, pageNumber);

		logger.info("Companies list retrieved");

		return companiesPage;
	}

	public Company getCompany(long id) {

		Company company = jdbcTemplate.queryForObject("SELECT * FROM company WHERE id = ?;", new CompanyMapper(), id);

		logger.info("Company retrieved");
		return company;
	}

	@Override
	public Company addCompany(Company company) {

		if (company == null || StringUtils.isBlank(company.getName())) {
			throw new DaoException("Company DAO error in addCompany method, name is mandatory");
		} else {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("name", company.getName());
			
			namedParameterJdbcTemplate.update("INSERT INTO company(name) VALUES (:name);", parameters, keyHolder);

			company.setId(keyHolder.getKey().longValue());
		}

		logger.info("Company successfully added");
		return company;
	}

	@Override
	public List<Company> listCompanies() {

		List<Company> companiesList = jdbcTemplate.query("SELECT * FROM company ORDER BY name;", new CompanyMapper());

		logger.info("Companies list retrieved");

		return companiesList;
	}

	@Override
	public void removeCompany(long id) {

		jdbcTemplate.update("DELETE FROM company WHERE id = ?;", id);

		logger.info("Company successfully removed");
	}

}