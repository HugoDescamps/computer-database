package com.excilys.cdb.webapp;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.dto.mapper.CompanyDTOMapper;
import com.excilys.cdb.dto.mapper.ComputerDTOMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.webapp.validator.Validator;

@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {

	@Autowired
	@Qualifier("computerService")
	private ComputerService computerService;

	@Autowired
	@Qualifier("companyService")
	private CompanyService companyService;

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setAttribute("companiesList", CompanyDTOMapper.createDTO(companyService.getCompanies()));

		req.setAttribute("computer",
				ComputerDTOMapper.createDTO(computerService.getComputer(Integer.parseInt(req.getParameter("id")))));

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		LocalDate introducedDate = null;
		LocalDate discontinuedDate = null;
		Integer companyId = null;

		if (!req.getParameter("introduced").equals("")) {
			introducedDate = LocalDate.parse(req.getParameter("introduced"));
		}

		if (!req.getParameter("discontinued").equals("")) {
			discontinuedDate = LocalDate.parse(req.getParameter("discontinued"));
		}

		if (!req.getParameter("companyId").equals("0")) {
			companyId = Integer.parseInt(req.getParameter("companyId"));
		}

		boolean nameValidator = Validator.nameValidator(req.getParameter("computerName"));
		boolean datesValidator = Validator.datesValidator(introducedDate, discontinuedDate);

		if (!datesValidator || !nameValidator) {

			if (!nameValidator) {
				req.setAttribute("inputError", "You must enter a valid name");
			} else {
				req.setAttribute("inputError", "Discontinued date must be after introduced date");
			}

			req.setAttribute("companiesList", CompanyDTOMapper.createDTO(companyService.getCompanies()));

			req.setAttribute("computer", ComputerDTOMapper
					.createDTO(computerService.getComputer(Integer.parseInt(req.getParameter("computerId")))));

			resp.sendRedirect(this.getServletContext().getContextPath() + "/editComputer");

		} else {

			Computer computer = new Computer();

			computer.setId(Integer.parseInt(req.getParameter("computerId")));

			computer.setName(req.getParameter("computerName"));

			if (introducedDate != null) {
				computer.setIntroduced(introducedDate);
			}

			if (discontinuedDate != null) {
				computer.setDiscontinued(discontinuedDate);
			}

			if (companyId != null) {
				computer.setCompany(new Company(companyId, null));
			}

			computerService.updateComputer(computer);

			resp.sendRedirect(this.getServletContext().getContextPath() + "/dashboard");
		}

	}

}
