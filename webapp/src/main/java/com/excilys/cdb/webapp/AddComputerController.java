package com.excilys.cdb.webapp;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.binding.CompanyDTOMapper;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.persistence.DaoException;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.webapp.validator.Validator;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {

	@Autowired
	private ComputerService computerService;

	@Autowired
	private CompanyService companyService;;

	@GetMapping
	protected ModelAndView doGet() {

		ModelAndView modelAndView = new ModelAndView("/WEB-INF/views/addComputer");

		modelAndView.addObject("companiesList", CompanyDTOMapper.createDTO(companyService.getCompanies()));

		return modelAndView;

	}

	@PostMapping
	protected ModelAndView doPost(@RequestParam Map<String, String> parameters) {

		ModelAndView modelAndView = new ModelAndView();

		LocalDate introducedDate = null;
		LocalDate discontinuedDate = null;
		Integer companyId = null;

		if (!parameters.get("introduced").equals("")) {
			introducedDate = LocalDate.parse(parameters.get("introduced"));
		}

		if (!parameters.get("discontinued").equals("")) {
			discontinuedDate = LocalDate.parse(parameters.get("discontinued"));
		}

		if (!parameters.get("companyId").equals("0")) {
			companyId = Integer.parseInt(parameters.get("companyId"));
		}

		boolean nameValidator = Validator.nameValidator(parameters.get("computerName"));
		boolean datesValidator = Validator.datesValidator(introducedDate, discontinuedDate);

		if (!datesValidator || !nameValidator) {
			modelAndView.addObject("computerName", parameters.get("computerName"));
			modelAndView.addObject("introduced", parameters.get("introduced"));
			modelAndView.addObject("discontinued", parameters.get("discontinued"));
			modelAndView.addObject("companyId", parameters.get("companyId"));
			modelAndView.addObject("companiesList", CompanyDTOMapper.createDTO(companyService.getCompanies()));

			if (!nameValidator) {
				modelAndView.addObject("inputError", "forms.nameError");
			} else {
				modelAndView.addObject("inputError", "forms.dateError");
			}

			modelAndView.setViewName("/WEB-INF/views/addComputer");

		} else {

			Computer computer = new Computer();

			computer.setName(parameters.get("computerName"));

			if (introducedDate != null) {
				computer.setIntroduced(introducedDate);
			}

			if (discontinuedDate != null) {
				computer.setDiscontinued(discontinuedDate);
			}

			if (companyId != null) {
				computer.setCompany(new Company(companyId, null));
			}

			try {
				computerService.addComputer(computer);
			} catch (DaoException e) {
				modelAndView.addObject("inputError", "forms.exception");

				modelAndView.setViewName("/WEB-INF/views/addComputer");

				return modelAndView;
			}

			modelAndView.setViewName("redirect:/dashboard");
		}

		return modelAndView;
	}

}
