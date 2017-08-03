package com.excilys.cdb.binding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.dto.ComputerDTO;

public class ComputerDTOMapper {
	
	/**
	 * Returns a ComputerDTO object corresponding to the given Computer & DateTimeFormatter arguments
	 * @param computer Computer object
	 * @param formatter DateTimeFormatter used to create the computer's introducedDate & discontinuedDate fields
	 * @return corresponding ComputerDTO object
	 */

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
	
	/**
	 * Returns a list of ComputerDTO object corresponding to the given list of Computer & DateTimeFormatter arguments
	 * @param computers list of Computer object
	 * @param formatter DateTimeFormatter used to create the computers' introducedDate & discontinuedDate fields
	 * @return corresponding list of ComputerDTO objects
	 */

	public static List<ComputerDTO> createDTO(List<Computer> computers, DateTimeFormatter formatter) {
		List<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();

		for (Computer computer : computers) {
			computerDTOList.add(createDTO(computer, formatter));
		}
		return computerDTOList;

	}
	
	/**
	 * Returns a Computer corresponding to the given computerDTO argument
	 * @param computerDTO ComputerDTO object
	 * @return corresponding Computer object
	 */

	public static Computer createComputer(ComputerDTO computerDTO) {

		Computer computer = new Computer();

		computer.setId(computerDTO.getId());
		computer.setName(computerDTO.getName());
		
		if (StringUtils.isNotBlank(computerDTO.getIntroduced())) {
			
			computer.setIntroduced(LocalDate.parse(computerDTO.getIntroduced()));
		}

		if (StringUtils.isNotBlank(computerDTO.getDiscontinued())) {
			computer.setDiscontinued(LocalDate.parse(computerDTO.getDiscontinued()));
		}

		if (computerDTO.getCompany() != null) {
			computer.setCompany(CompanyDTOMapper.createCompany(computerDTO.getCompany()));
		}

		return computer;
	}

}
