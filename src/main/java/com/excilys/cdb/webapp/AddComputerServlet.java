package com.excilys.cdb.webapp;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.serviceImpl.CompanyServiceImpl;
import com.excilys.cdb.service.serviceImpl.ComputerServiceImpl;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {

	private CompanyServiceImpl companyServiceImpl = CompanyServiceImpl.INSTANCE;
	private ComputerServiceImpl computerServiceImpl = ComputerServiceImpl.INSTANCE;

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setAttribute("companiesList", companyServiceImpl.getCompanies());

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String computerName = req.getParameter("computerName");
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

		boolean datesValidation = datesValidation(introducedDate, discontinuedDate);

		if (!datesValidation) {

			req.setAttribute("datesError", "Discontinued date must be after introduced date");
			req.setAttribute("computerName", computerName);
			req.setAttribute("introduced", req.getParameter("introduced"));
			req.setAttribute("discontinued", req.getParameter("discontinued"));
			req.setAttribute("companyId", req.getParameter("companyId"));
			req.setAttribute("companiesList", companyServiceImpl.getCompanies());

			this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(req, resp);

		} else {
			
			Computer computer = new Computer();
			
			computer.setName(computerName);

			if (introducedDate != null) {
				computer.setIntroduced(introducedDate);
			}

			if (discontinuedDate != null) {
				computer.setDiscontinued(discontinuedDate);
			}

			if (companyId != null) {
				computer.setCompany(new Company(companyId, null));
			}
			
			computerServiceImpl.addComputer(computer);

			resp.sendRedirect(this.getServletContext().getContextPath() + "/dashboard");
		}
	}

	private boolean datesValidation(LocalDate introducedDate, LocalDate discontinuedDate) {

		boolean res;

		if (introducedDate == null || discontinuedDate == null) {
			res = true;
		} else {
			if (discontinuedDate.isAfter(introducedDate)) {
				res = true;
			} else {
				res = false;
			}
		}

		return res;
	}

}
