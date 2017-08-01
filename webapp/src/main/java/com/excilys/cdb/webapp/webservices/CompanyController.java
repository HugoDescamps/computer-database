package com.excilys.cdb.webapp.webservices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.binding.CompanyDTOMapper;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.dto.CompanyDTO;
import com.excilys.cdb.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	private CompanyService companyService;

	@GetMapping(value = "/get/{id}")
	public CompanyDTO getCompany(@PathVariable long id) {

		CompanyDTO companyDTO = CompanyDTOMapper.createDTO(companyService.getCompany(id));

		logger.info("Company successfully retrieved");
		return companyDTO;

	}

	@GetMapping(value = "/get/{pageNumber}/{pageSize}")
	public List<CompanyDTO> getCompanies(@PathVariable int pageNumber, @PathVariable int pageSize) {

		List<CompanyDTO> companyDTOList = CompanyDTOMapper
				.createDTO(companyService.getCompanies(pageNumber, pageSize).getObjectsList());

		logger.info("Companies successfully retrieved");
		return companyDTOList;
	}

	@GetMapping(value = "/get")
	public List<CompanyDTO> getCompanies() {

		List<CompanyDTO> companyDTOList = CompanyDTOMapper.createDTO(companyService.getCompanies());

		logger.info("Companies successfully retrieved");
		return companyDTOList;

	}

	@PostMapping(value = "/add")
	public CompanyDTO addCompany(@RequestBody CompanyDTO companyDTO) {

		Company company = CompanyDTOMapper.createCompany(companyDTO);

		companyService.addCompany(company);

		logger.info("Company successfully added");
		return CompanyDTOMapper.createDTO(company);
	}

	@PutMapping(value = "/update")
	public CompanyDTO updateCompany(@RequestBody CompanyDTO companyDTO) {

		Company company = CompanyDTOMapper.createCompany(companyDTO);

		companyService.updateCompany(company);

		logger.info("Company successfully updated");
		return CompanyDTOMapper.createDTO(company);
	}

	@DeleteMapping(value = "/delete/{id}")
	public String delete(@PathVariable long id) {

		companyService.removeCompany(id);

		logger.info("Company successfully removed");

		return "Company of id " + id + " successfully removed";
	}

}
