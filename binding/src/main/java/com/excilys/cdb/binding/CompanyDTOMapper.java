package com.excilys.cdb.binding;

import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.dto.CompanyDTO;

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
