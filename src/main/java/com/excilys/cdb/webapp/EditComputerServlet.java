package com.excilys.cdb.webapp;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.mapper.CompanyDTOMapper;
import com.excilys.cdb.dto.mapper.ComputerDTOMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.serviceImpl.CompanyServiceImpl;
import com.excilys.cdb.service.serviceImpl.ComputerServiceImpl;
import com.excilys.cdb.webapp.validator.Validator;

@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {

	ComputerServiceImpl computerServiceImpl = ComputerServiceImpl.INSTANCE;
	CompanyServiceImpl companyServiceImpl = CompanyServiceImpl.INSTANCE;

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setAttribute("companiesList", CompanyDTOMapper.createDTO(companyServiceImpl.getCompanies()));

		req.setAttribute("computer",
				ComputerDTOMapper.createDTO(computerServiceImpl.getComputer(Integer.parseInt(req.getParameter("id")))));

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
			
			req.setAttribute("companiesList", CompanyDTOMapper.createDTO(companyServiceImpl.getCompanies()));

			req.setAttribute("computer", ComputerDTOMapper
					.createDTO(computerServiceImpl.getComputer(Integer.parseInt(req.getParameter("computerId")))));
			
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

			computerServiceImpl.updateComputer(computer);

			resp.sendRedirect(this.getServletContext().getContextPath() + "/dashboard");
		}

	}

}
