package com.excilys.cdb.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyDTOMapper {

	public static CompanyDTO createDTO(Company company) {
		
		CompanyDTO companyDTO = new CompanyDTO();

		companyDTO.setId(company.getId());
		companyDTO.setName(company.getName());

		return companyDTO;
	}

	public static List<CompanyDTO> createDTO(List<Company> companies) {
		List<CompanyDTO> companyDTOList = new ArrayList<CompanyDTO>();
		
		for(Company company : companies) {
			companyDTOList.add(createDTO(company));
		}
		return companyDTOList;
	}

}
