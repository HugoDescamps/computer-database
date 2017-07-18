package com.excilys.cdb.persistence.persistenceImpl;

import java.sql.Timestamp;
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

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ComputerDao;
import com.excilys.cdb.persistence.DaoException;
import com.excilys.cdb.persistence.mapper.ComputerMapper;
import com.zaxxer.hikari.HikariDataSource;

@Repository("computerDao")
public class ComputerDaoImpl implements ComputerDao {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

	private ComputerDaoImpl() {
	}

	@Autowired
	public void setDataSource(HikariDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Page<Computer> listComputers(int pageNumber, int pageSize, String search, OrderColumn column, OrderWay way) {

		String requestColumn = "";

		switch (column) {
		case COMPUTER:
			requestColumn = "computer.name";
			break;
		case COMPANY:
			requestColumn = "company.name";
			break;
		default:
			requestColumn = "computer.id";
			break;
		}

		List<Computer> computersList = jdbcTemplate.query(
				"SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY "
						+ requestColumn + " " + way + " LIMIT ?,?;",
				new ComputerMapper(), "%" + search + "%", "%" + search + "%", (pageNumber - 1) * pageSize, pageSize);

		Page<Computer> computersPage = new Page<Computer>(computersList, pageSize, pageNumber);

		logger.info("Computers list retrieved");

		return computersPage;

	}

	@Override
	public int countComputers(String search) {

		int rowCount = jdbcTemplate.queryForObject(
				"SELECT count(*) FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?;",
				Integer.class, "%" + search + "%", "%" + search + "%");

		logger.info("Computers count retrieved");
		return rowCount;
	}

	@Override
	public Computer getComputer(long id) {

		Computer computer = jdbcTemplate.queryForObject(
				"SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?;",
				new ComputerMapper(), id);

		logger.info("Computer retrieved");
		return computer;
	}

	@Override
	public Computer addComputer(Computer computer) {

		if (computer == null || StringUtils.isBlank(computer.getName())) {
			throw new DaoException("Computer DAO error in addComputer method, name is mandatory");
		} else {

			Timestamp introducedDate = null;
			Timestamp discontinuedDate = null;
			Long company_id = null;

			if (computer.getIntroduced() != null) {
				introducedDate = Timestamp.valueOf(computer.getIntroduced().atStartOfDay());
			}

			if (computer.getDiscontinued() != null) {
				discontinuedDate = Timestamp.valueOf(computer.getDiscontinued().atStartOfDay());
			}

			if (computer.getCompany() != null) {
				company_id = computer.getCompany().getId();
			}

			KeyHolder keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("name", computer.getName());
			parameters.addValue("introduced", introducedDate);
			parameters.addValue("discontinued", discontinuedDate);
			parameters.addValue("company_id", company_id);

			namedParameterJdbcTemplate.update(
					"INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (:name, :introduced, :discontinued, :company_id);",
					parameters, keyHolder);

			computer.setId(keyHolder.getKey().longValue());
		}

		logger.info("Computer successfully added");
		return computer;

	}

	@Override
	public boolean updateComputer(Computer computer) {

		if (computer == null || StringUtils.isBlank(computer.getName())) {
			throw new DaoException("Computer DAO error in addComputer method, name is mandatory");
		} else {

			Timestamp introducedDate = null;
			Timestamp discontinuedDate = null;
			Long company_id = null;

			if (computer.getIntroduced() != null) {
				introducedDate = Timestamp.valueOf(computer.getIntroduced().atStartOfDay());
			}

			if (computer.getDiscontinued() != null) {
				discontinuedDate = Timestamp.valueOf(computer.getDiscontinued().atStartOfDay());
			}

			if (computer.getCompany() != null) {
				company_id = computer.getCompany().getId();
			}

			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("name", computer.getName());
			parameters.addValue("introduced", introducedDate);
			parameters.addValue("discontinued", discontinuedDate);
			parameters.addValue("company_id", company_id);
			parameters.addValue("id", computer.getId());

			namedParameterJdbcTemplate.update(
					"UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id WHERE id = :id;",
					parameters);
		}

		logger.info("Computer successfully updated");
		return true;
	}

	@Override
	public void removeComputer(long id) {

		jdbcTemplate.update("DELETE FROM computer WHERE id = ?;", id);
		
		logger.info("Computer successfully removed");
	}

	@Override
	public void removeComputers(long company_id) {

		jdbcTemplate.update("DELETE FROM computer WHERE company_id = ?;", company_id);

		logger.info("Company's computers removed");
	}

}
