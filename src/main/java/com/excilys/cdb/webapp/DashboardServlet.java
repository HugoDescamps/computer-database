package com.excilys.cdb.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.service.serviceImpl.ComputerServiceImpl;


@WebServlet({"/dashboard", "/"})
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ComputerServiceImpl computerServiceImpl = ComputerServiceImpl.INSTANCE;
		
		req.setAttribute("computersPage", computerServiceImpl.getComputers(1, 12));
		req.setAttribute("computersCount", computerServiceImpl.countComputers());
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req,  resp);

	}

}