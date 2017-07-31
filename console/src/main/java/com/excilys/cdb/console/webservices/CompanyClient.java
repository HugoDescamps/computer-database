package com.excilys.cdb.console.webservices;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.CompanyDTOMapper;
import com.excilys.cdb.core.dto.CompanyDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CompanyClient {

	private static final Logger logger = LoggerFactory.getLogger(CompanyClient.class);

	public static void getCompanies() {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/webapp/company/get");

		Response response = target.request().get();
		String value = response.readEntity(String.class);

		try {
			List<CompanyDTO> companyDTOList = new ObjectMapper().readValue(value,
					new TypeReference<List<CompanyDTO>>() {
					});
			for (CompanyDTO companyDTO : companyDTOList) {
				System.out.println(CompanyDTOMapper.createCompany(companyDTO).toString());
			}
		} catch (IOException e) {
			logger.error("Could not retrieve companies in Company Client " + e);
		}

		response.close();
	}

	public static void getCompanies(int pageNumber, int pageSize) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client
				.target("http://localhost:8080/webapp/company/get/" + pageNumber + "/" + pageSize);

		Response response = target.request().get();
		String value = response.readEntity(String.class);

		try {
			List<CompanyDTO> companyDTOList = new ObjectMapper().readValue(value,
					new TypeReference<List<CompanyDTO>>() {
					});
			for (CompanyDTO companyDTO : companyDTOList) {
				System.out.println(CompanyDTOMapper.createCompany(companyDTO).toString());
			}
		} catch (IOException e) {
			logger.error("Could not retrieve companies in Company Client " + e);
		}

		response.close();
	}

	public static void getCompany(long id) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/webapp/company/get/" + id);

		Response response = target.request().get();
		String value = response.readEntity(String.class);

		try {
			CompanyDTO company = new ObjectMapper().readValue(value, new TypeReference<CompanyDTO>() {
			});
			System.out.println(CompanyDTOMapper.createCompany(company));
		} catch (IOException e) {
			logger.error("Could not retrieve company in Company Client " + e);
		}

		response.close();
	}

}