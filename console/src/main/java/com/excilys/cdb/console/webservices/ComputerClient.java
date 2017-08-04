package com.excilys.cdb.console.webservices;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.ComputerDTOMapper;
import com.excilys.cdb.core.dto.ComputerDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ComputerClient {

	private static final Logger logger = LoggerFactory.getLogger(ComputerClient.class);
	
	/**
	 * CLI WebService used to retrieve computers' informations according to the given page, its size and the search in argument
	 * @param pageNumber Number of the page (1 is minimum)
	 * @param pageSize Number of computers to be displayed in the page
	 * @param search Piece of string to be found in the computer's name or its company's name
	 */

	public static void getComputers(int pageNumber, int pageSize, String search) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client
				.target("http://localhost:8080/webapp/computer/get/" + pageNumber + "/" + pageSize + "/" + search);

		Response response = target.request().get();
		String value = response.readEntity(String.class);

		try {
			List<ComputerDTO> computerDTOList = new ObjectMapper().readValue(value,
					new TypeReference<List<ComputerDTO>>() {
					});
			for (ComputerDTO computerDTO : computerDTOList) {
				System.out.println(ComputerDTOMapper.createComputer(computerDTO).toString());
			}
		} catch (IOException e) {
			logger.error("Could not retrieve computers in Computer Client " + e);
		}

		response.close();
	}
	
	/**
	 * CLI WebService used to retrieve a single computer' informations
	 * @param id Computer's id we want to retrieve
	 */

	public static void getComputer(long id) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/webapp/computer/get/" + id);

		Response response = target.request().get();
		String value = response.readEntity(String.class);

		try {
			ComputerDTO computer = new ObjectMapper().readValue(value, new TypeReference<ComputerDTO>() {
			});
			System.out.println(ComputerDTOMapper.createComputer(computer));
		} catch (IOException e) {
			logger.error("Could not retrieve computer in Computer Client " + e);
		}

		response.close();
	}
	
	/**
	 * CLI WebService used to add a computer
	 * @param computerDTO ComputerDTO object to be added
	 */
	
	public static void addComputer(ComputerDTO computerDTO) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/webapp/company/add");

		Response response = target.request().post(Entity.json(computerDTO));
		String value = response.readEntity(String.class);

		try {
			ComputerDTO computer = new ObjectMapper().readValue(value, new TypeReference<ComputerDTO>() {
			});
			System.out.println(ComputerDTOMapper.createComputer(computer));
		} catch (IOException e) {
			logger.error("Could not retrieve computer in Computer Client " + e);
		}

		response.close();
	}
}
