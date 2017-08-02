package com.excilys.cdb.webapp;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.binding.ComputerDTOMapper;
import com.excilys.cdb.core.OrderColumnEnum;
import com.excilys.cdb.core.OrderWayEnum;
import com.excilys.cdb.core.dto.ComputerDTO;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private ComputerService computerService;

	private static DateTimeFormatter formatter;

	@GetMapping
	protected ModelAndView doGet(@RequestParam Map<String, String> parameters, Locale locale, Authentication authentication) {

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

		modelAndView.addObject("role", authentication.getAuthorities().iterator().next());
		modelAndView.addObject("page", page);
		modelAndView.addObject("size", size);
		modelAndView.addObject("search", search);
		modelAndView.addObject("order", order);

		OrderColumnEnum orderColumn = OrderColumnEnum.NULL;
		OrderWayEnum orderWay = OrderWayEnum.ASC;

		switch (order) {
		case "computerAsc":
			orderColumn = OrderColumnEnum.COMPUTER;
			orderWay = OrderWayEnum.ASC;
			break;
		case "computerDesc":
			orderColumn = OrderColumnEnum.COMPUTER;
			orderWay = OrderWayEnum.DESC;
			break;
		case "companyAsc":
			orderColumn = OrderColumnEnum.COMPANY;
			orderWay = OrderWayEnum.ASC;
			break;
		case "companyDesc":
			orderColumn = OrderColumnEnum.COMPANY;
			orderWay = OrderWayEnum.DESC;
			break;
		default:
			break;
		}
		
		switch(locale.toString()) {
		case "fr" :
			formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			break;
		default :
			formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
			break;
		}

		computersList = ComputerDTOMapper.createDTO(
				computerService.getComputers(page, size, search, orderColumn, orderWay).getObjectsList(), formatter);

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
