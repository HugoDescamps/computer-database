package com.excilys.cdb.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.excilys.cdb.binding.CompanyDTOMapper;
import com.excilys.cdb.core.dto.CompanyDTO;
import com.excilys.cdb.service.CompanyService;

@Controller
@RequestMapping("/company")
public class CompanyController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	private CompanyService companyService;

	@GetMapping(value = "/get/{id}")
	@ResponseBody
	public CompanyDTO getCompany(@PathVariable long id) {
		
		CompanyDTO companyDTO = CompanyDTOMapper.createDTO(companyService.getCompany(id));

		logger.info("Company successfully retrieved");
		return companyDTO;

	}

}
