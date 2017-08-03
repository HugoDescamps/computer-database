package com.excilys.cdb.binding;

import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.dto.CompanyDTO;

public class CompanyDTOMapper {
	
	/**
	 * Returns a CompanyDTO object corresponding to the given Company argument
	 * @param company Company object
	 * @return corresponding CompanyDTO object
	 */

	public static CompanyDTO createDTO(Company company) {
		
		CompanyDTO companyDTO = new CompanyDTO();

		companyDTO.setId(company.getId());
		companyDTO.setName(company.getName());

		return companyDTO;
	}
	
	/**
	 * Returns a list of CompanyDTO objects corresponding to the given list of Company argument
	 * @param companies list of Company objects
	 * @return corresponding list of CompanyDTO objects
	 */

	public static List<CompanyDTO> createDTO(List<Company> companies) {
		List<CompanyDTO> companyDTOList = new ArrayList<CompanyDTO>();
		
		for(Company company : companies) {
			companyDTOList.add(createDTO(company));
		}
		return companyDTOList;
	}
	
	/**
	 * Returns a Company corresponding to the given companyDTO argument
	 * @param companyDTO CompanyDTO object
	 * @return corresponding Company object
	 */
	
	public static Company createCompany(CompanyDTO companyDTO) {
		
		return new Company(companyDTO.getId(), companyDTO.getName());

	}

}
