package com.excilys.cdb.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.mapper.ComputerDTOMapper;
import com.excilys.cdb.persistence.ComputerDao.OrderColumn;
import com.excilys.cdb.persistence.ComputerDao.OrderWay;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private ComputerService computerService;

	@GetMapping
	protected ModelAndView doGet(@RequestParam Map<String, String> parameters) {
		
		ModelAndView modelAndView = new ModelAndView("/WEB-INF/views/dashboard");

		int page = 1;
		int size = 50;

		if (StringUtils.isNotBlank(parameters.get("page"))) {
			page = Integer.parseInt(parameters.get("page"));
		}

		if (StringUtils.isNotBlank(parameters.get("size"))) {
			size = Integer.parseInt(parameters.get("size"));
		}

		List<ComputerDTO> computersList;

		String search = "";
		String order = "";

		if (StringUtils.isNotBlank(parameters.get("search"))) {
			search = parameters.get("search").trim();
		}

		if (StringUtils.isNotBlank(parameters.get("order"))) {
			order = parameters.get("order").trim();
		}

		modelAndView.addObject("page", page);
		modelAndView.addObject("size", size);
		modelAndView.addObject("search", search);
		modelAndView.addObject("order", order);

		OrderColumn orderColumn = OrderColumn.NULL;
		OrderWay orderWay = OrderWay.ASC;

		switch (order) {
		case "computerAsc":
			orderColumn = OrderColumn.COMPUTER;
			orderWay = OrderWay.ASC;
			break;
		case "computerDesc":
			orderColumn = OrderColumn.COMPUTER;
			orderWay = OrderWay.DESC;
			break;
		case "companyAsc":
			orderColumn = OrderColumn.COMPANY;
			orderWay = OrderWay.ASC;
			break;
		case "companyDesc":
			orderColumn = OrderColumn.COMPANY;
			orderWay = OrderWay.DESC;
			break;
		default:
			break;
		}

		computersList = ComputerDTOMapper
				.createDTO(computerService.getComputers(page, size, search, orderColumn, orderWay).getObjectsList());

		modelAndView.addObject("computersDTO", computersList);
		
		int computersCount = computerService.countComputers(search);
		
		modelAndView.addObject("computersCount", computersCount);

		int numberOfPages = countPages(computersCount, size);

		modelAndView.addObject("numberOfPages", numberOfPages);
		modelAndView.addObject("numberOfPagesArray", storePagesNumbers(page, numberOfPages));
		
		return modelAndView;
	}

	@PostMapping
	protected String doPost(@RequestParam String selection) {

		String selectedComputersList = selection;

		for (String id : selectedComputersList.split(",")) {
			computerService.removeComputer(Integer.parseInt(id));
		}
		
		return "redirect:/dashboard";
	}

	private int countPages(int computersCount, int size) {

		int numberOfPages = computersCount / size;

		if (computersCount % size != 0) {
			numberOfPages++;
		}

		return numberOfPages;

	}

	private List<Integer> storePagesNumbers(int page, int numberOfPages) {

		List<Integer> numberOfPagesArray = new ArrayList<>();

		if (page - 2 > 0) {
			numberOfPagesArray.add(page - 2);
			numberOfPagesArray.add(page - 1);
		} else if (page - 1 > 0) {
			numberOfPagesArray.add(page - 1);
		}

		numberOfPagesArray.add(page);

		if (page + 2 <= numberOfPages) {
			numberOfPagesArray.add(page + 1);
			numberOfPagesArray.add(page + 2);
		} else if (page + 1 <= numberOfPages) {
			numberOfPagesArray.add(page + 1);
		}

		return numberOfPagesArray;

	}

}
