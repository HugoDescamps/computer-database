package com.excilys.cdb.webapp.webservices;

import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.binding.ComputerDTOMapper;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.OrderColumnEnum;
import com.excilys.cdb.core.OrderWayEnum;
import com.excilys.cdb.core.dto.ComputerDTO;
import com.excilys.cdb.service.ComputerService;

@RestController
@RequestMapping("/computer")
public class ComputerController {

	private static final Logger logger = LoggerFactory.getLogger(ComputerController.class);

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Autowired
	private ComputerService computerService;

	@GetMapping(value = "/get/{id}")
	@ResponseBody
	public ComputerDTO getComputer(@PathVariable long id) {

		ComputerDTO computerDTO = ComputerDTOMapper.createDTO(computerService.getComputer(id), formatter);

		logger.info("Computer successfully retrieved");
		return computerDTO;

	}

	@GetMapping(value = "/get/{pageNumber}/{pageSize}")
	@ResponseBody
	public List<ComputerDTO> getComputers(@PathVariable int pageNumber, @PathVariable int pageSize) {

		List<ComputerDTO> computerDTOList = ComputerDTOMapper.createDTO(computerService
				.getComputers(pageNumber, pageSize, "", OrderColumnEnum.NULL, OrderWayEnum.ASC).getObjectsList(),
				formatter);

		logger.info("Computers successfully retrieved");
		return computerDTOList;
	}

	@GetMapping(value = "/get/{pageNumber}/{pageSize}/{search}")
	@ResponseBody
	public List<ComputerDTO> getComputers(@PathVariable int pageNumber, @PathVariable int pageSize,
			@PathVariable String search) {

		List<ComputerDTO> computerDTOList = ComputerDTOMapper.createDTO(computerService
				.getComputers(pageNumber, pageSize, search, OrderColumnEnum.NULL, OrderWayEnum.ASC).getObjectsList(),
				formatter);

		logger.info("Computers successfully retrieved");
		return computerDTOList;
	}

	@PostMapping(value = "/add")
	@ResponseBody
	public ComputerDTO addComputer(@RequestBody ComputerDTO computerDTO) {

		Computer computer = ComputerDTOMapper.createComputer(computerDTO);

		computerService.addComputer(computer);

		logger.info("Computer successfully added");
		return ComputerDTOMapper.createDTO(computer, formatter);
	}

	@PutMapping(value = "/update")
	@ResponseBody
	public ComputerDTO updateComputer(@RequestBody ComputerDTO computerDTO) {

		Computer computer = ComputerDTOMapper.createComputer(computerDTO);

		computerService.updateComputer(computer);

		logger.info("Computer successfully updated");
		return ComputerDTOMapper.createDTO(computer, formatter);
	}

	@DeleteMapping(value = "/delete/{id}")
	public String delete(@PathVariable long id) {

		computerService.removeComputer(id);

		logger.info("Computer successfully removed");

		return "Computer of id " + id + " successfully removed";
	}
}
