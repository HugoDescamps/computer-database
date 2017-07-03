package com.excilys.cdb.persistence.persistenceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ComputerDao;
import com.excilys.cdb.persistence.DaoException;
import com.excilys.cdb.persistence.mapper.ComputerMapper;

public enum ComputerDaoImpl implements ComputerDao {
	INSTANCE;

	private DataBaseConnector dataBaseConnection;
	static final Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

	private ComputerDaoImpl() {
		dataBaseConnection = DataBaseConnector.INSTANCE;
	}

	@Override
	public Page<Computer> listComputers(int pageNumber, int pageSize, String search, OrderColumn column, OrderWay way) {

		Page<Computer> computersPage = new Page<Computer>();

		List<Computer> computersList = new ArrayList<Computer>();

		PreparedStatement listComputersStatement = null;
		ResultSet listComputersResult = null;

		try (Connection connection = dataBaseConnection.connect();) {

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

			listComputersStatement = connection.prepareStatement(
					"SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY "
							+ requestColumn + " " + way + " LIMIT ?,?;");

			listComputersStatement.setString(1, "%" + search + "%");

			listComputersStatement.setString(2, "%" + search + "%");

			listComputersStatement.setInt(3, (pageNumber - 1) * pageSize);

			listComputersStatement.setInt(4, pageSize);

			listComputersResult = listComputersStatement.executeQuery();

			computersList = ComputerMapper.getComputers(listComputersResult);

			computersPage.setObjectsList(computersList);
			computersPage.setNumber(pageNumber);
			computersPage.setSize(pageSize);

		} catch (SQLException e) {
			throw new DaoException("Computer DAO error in listComputersSearch method " + e.getMessage());
		}
		logger.info("Computers list retrieved");
		return computersPage;
	}

	@Override
	public int countComputers(String search) {

		int computersCount = 0;

		ResultSet countComputersResultset = null;

		try (Connection connection = dataBaseConnection.connect();
				PreparedStatement computersCountStatement = connection.prepareStatement(
						"SELECT count(*) FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?;");) {

			computersCountStatement.setString(1, "%" + search + "%");

			computersCountStatement.setString(2, "%" + search + "%");

			countComputersResultset = computersCountStatement.executeQuery();

			computersCount = ComputerMapper.countComputers(countComputersResultset);

		} catch (SQLException e) {
			throw new DaoException("Computer DAO error in countComputers method " + e.getMessage());
		}
		logger.info("Computers count retrieved");
		return computersCount;
	}

	@Override
	public Computer getComputer(long id) {

		Computer computer = null;

		ResultSet getComputerResult = null;

		try (Connection connection = dataBaseConnection.connect();
				PreparedStatement getComputerStatement = connection.prepareStatement(
						"SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?;");) {

			getComputerStatement.setLong(1, id);

			getComputerResult = getComputerStatement.executeQuery();

			computer = ComputerMapper.getComputer(getComputerResult);

		} catch (SQLException e) {
			throw new DaoException("Computer DAO error in getComputer method " + e.getMessage());
		}

		logger.info("Computer retrieved");
		return computer;
	}

	@Override
	public Computer addComputer(Computer computer) {

		ResultSet addComputerResult = null;

		try (Connection connection = dataBaseConnection.connect();
				PreparedStatement addComputerStatement = connection.prepareStatement(
						"INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);",
						Statement.RETURN_GENERATED_KEYS);) {

			if (computer != null && StringUtils.isNotBlank(computer.getName())) {
				addComputerStatement.setString(1, computer.getName());
			} else {
				throw new DaoException("Computer DAO error in addComputer method, name is mandatory");
			}

			Timestamp introducedDate = null;
			Timestamp discontinuedDate = null;

			if (computer.getIntroduced() != null) {
				introducedDate = Timestamp.valueOf(computer.getIntroduced().atStartOfDay());
			}
			addComputerStatement.setTimestamp(2, introducedDate);

			if (computer.getDiscontinued() != null) {
				discontinuedDate = Timestamp.valueOf(computer.getDiscontinued().atStartOfDay());
			}
			addComputerStatement.setTimestamp(3, discontinuedDate);

			if (computer.getCompany() != null) {
				addComputerStatement.setLong(4, computer.getCompany().getId());
			} else {
				addComputerStatement.setNull(4, Types.BIGINT);
			}

			addComputerStatement.executeUpdate();

			addComputerResult = addComputerStatement.getGeneratedKeys();

			if (addComputerResult.next()) {
				computer.setId(addComputerResult.getLong(1));
			}

		} catch (SQLException e) {
			throw new DaoException("Computer DAO error in addComputer method " + e.getMessage());
		}
		logger.info("Computer successfully added");
		return computer;
	}

	@Override
	public boolean updateComputer(Computer computer) {

		try (Connection connection = dataBaseConnection.connect();
				PreparedStatement updateComputerStatement = connection.prepareStatement(
						"UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;");) {

			if (computer != null && StringUtils.isNotBlank(computer.getName())) {
				updateComputerStatement.setString(1, computer.getName());
			} else {
				throw new DaoException("Computer DAO error in addComputer method, name is mandatory");
			}

			Timestamp introducedDate = null;
			Timestamp discontinuedDate = null;

			if (computer.getIntroduced() != null) {
				introducedDate = Timestamp.valueOf(computer.getIntroduced().atStartOfDay());
			}
			updateComputerStatement.setTimestamp(2, introducedDate);

			if (computer.getDiscontinued() != null) {
				discontinuedDate = Timestamp.valueOf(computer.getDiscontinued().atStartOfDay());
			}
			updateComputerStatement.setTimestamp(3, discontinuedDate);

			if (computer.getCompany() != null) {
				updateComputerStatement.setLong(4, computer.getCompany().getId());
			} else {
				updateComputerStatement.setNull(4, Types.BIGINT);
			}

			updateComputerStatement.setLong(5, computer.getId());

			updateComputerStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException("Computer DAO error in updateComputer method " + e.getMessage());
		}
		logger.info("Computer successfully updated");
		return true;
	}

	@Override
	public void removeComputer(long id) {

		try (Connection connection = dataBaseConnection.connect();
				PreparedStatement removeComputerStatement = connection
						.prepareStatement("DELETE FROM computer WHERE id = ?;");) {

			removeComputerStatement.setLong(1, id);

			removeComputerStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException("Computer DAO error in removeComputer method " + e.getMessage());

		}
		logger.info("Computer successfully removed");
	}

	@Override
	public void removeComputers(Connection connection, long company_id) {

		try (PreparedStatement removeComputersStatement = connection
				.prepareStatement("DELETE FROM computer WHERE company_id = ?;");) {

			removeComputersStatement.setLong(1, company_id);

			removeComputersStatement.executeUpdate();

		} catch (SQLException e) {

			throw new DaoException("Computer DAO error in removeComputers method " + e.getMessage());
		}
		logger.info("Company's computers removed");
	}

}
