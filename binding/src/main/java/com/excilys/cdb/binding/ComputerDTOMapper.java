package com.excilys.cdb.binding;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.dto.ComputerDTO;

public class ComputerDTOMapper {

	public static ComputerDTO createDTO(Computer computer, DateTimeFormatter formatter) {

		ComputerDTO computerDTO = new ComputerDTO();

		computerDTO.setId(computer.getId());
		computerDTO.setName(computer.getName());

		if (computer.getIntroduced() != null) {

			computerDTO.setIntroduced(computer.getIntroduced().format(formatter));
		}
		if (computer.getDiscontinued() != null) {
			computerDTO.setDiscontinued(computer.getDiscontinued().format(formatter));
		}
		if (computer.getCompany() != null) {
			computerDTO.setCompany(CompanyDTOMapper
					.createDTO(new Company(computer.getCompany().getId(), computer.getCompany().getName())));
			;
		}

		return computerDTO;
	}

	public static List<ComputerDTO> createDTO(List<Computer> computers, DateTimeFormatter formatter) {
		List<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();

		for (Computer computer : computers) {
			computerDTOList.add(createDTO(computer, formatter));
		}
		return computerDTOList;

	}

}