package com.excilys.cdb.persistence.persistenceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ComputerDao;
import com.excilys.cdb.persistence.MyException;
import com.excilys.cdb.persistence.mapper.ComputerMapper;

public enum ComputerDaoImpl implements ComputerDao {
	INSTANCE;

	@Override
	public Page<Computer> listComputers(int pageNumber, int pageSize) {

		Page<Computer> computersPage = new Page<Computer>();

		List<Computer> computersList = new ArrayList<Computer>();

		try (Connection connection = DataBaseConnector.connect();
				PreparedStatement listComputersStatement = connection.prepareStatement(
						"SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT ?,?;");) {

			listComputersStatement.setInt(1, (pageNumber - 1) * pageSize);

			listComputersStatement.setInt(2, pageSize);

			ResultSet listComputersResult = listComputersStatement.executeQuery();

			computersList = ComputerMapper.getComputers(listComputersResult);

			computersPage.setObjectsList(computersList);
			computersPage.setNumber(pageNumber);
			computersPage.setSize(pageSize);

		} catch (Exception e) {
			throw new MyException("Computer DAO error in listComputers method");
		}
		System.out.println("Computers list retrieved");
		return computersPage;
	}

	@Override
	public Computer getComputer(long id) {

		Computer computer = null;

		ResultSet getComputerResult = null;

		try (Connection connection = DataBaseConnector.connect();
				PreparedStatement getComputerStatement = connection.prepareStatement(
						"SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?;");) {

			getComputerStatement.setLong(1, id);

			getComputerResult = getComputerStatement.executeQuery();

			computer = ComputerMapper.getComputer(getComputerResult);

		} catch (Exception e) {
			throw new MyException("Computer DAO error in getComputer method");
		}

		System.out.println("Computer retrieved");
		return computer;
	}

	@Override
	public Computer addComputer(Computer computer) {

		ResultSet addComputerResult = null;

		try (Connection connection = DataBaseConnector.connect();
				PreparedStatement addComputerStatement = connection.prepareStatement(
						"INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);");) {

			addComputerStatement.setString(1, computer.getName());

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
			} else {
				throw new SQLException("Could not create computer");
			}
		} catch (Exception e) {
			throw new MyException("Computer DAO error in addComputer method");
		}

		System.out.println("Computer successfully added");
		return computer;
	}

	@Override
	public boolean updateComputer(Computer computer) {

		PreparedStatement updateComputerStatement = null;

		try (Connection connection = DataBaseConnector.connect();) {
			updateComputerStatement = connection.prepareStatement(
					"UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;");

			updateComputerStatement.setString(1, computer.getName());

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

		} catch (Exception e) {
			throw new MyException("Computer DAO error in updateComputer method");
		}
		System.out.println("Computer successfully updated");
		return true;
	}

	@Override
	public boolean removeComputer(long id) {

		PreparedStatement removeComputerStatement = null;

		try (Connection connection = DataBaseConnector.connect();) {
			removeComputerStatement = connection.prepareStatement("DELETE FROM computer WHERE id = ?;");

			removeComputerStatement.setLong(1, id);

			removeComputerStatement.executeUpdate();

		} catch (Exception e) {
			throw new MyException("Computer DAO error in removeCOmputer method");
		}
		System.out.println("Computer successfully removed");
		return true;
	}

}
