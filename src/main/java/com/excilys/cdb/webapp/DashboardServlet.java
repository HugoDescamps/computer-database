package com.excilys.cdb.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.excilys.cdb.dto.mapper.ComputerDTOMapper;
import com.excilys.cdb.service.serviceImpl.ComputerServiceImpl;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	
	ComputerServiceImpl computerServiceImpl = ComputerServiceImpl.INSTANCE;

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int pageNumber = 1;

		if (StringUtils.isNotBlank(req.getParameter("pageNumber"))) {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		}

		req.setAttribute("computersDTO",
				ComputerDTOMapper.createDTO(computerServiceImpl.getComputers(pageNumber, 50).getObjectsList()));

		int computersCount = computerServiceImpl.countComputers();
		req.setAttribute("computersCount", computersCount);

		int numberOfPages = countPages(computersCount);

		req.setAttribute("numberOfPages", numberOfPages);
		req.setAttribute("numberOfPagesArray", storePagesNumbers(numberOfPages));

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);

	}

	private int countPages(int computersCount) {

		int numberOfPages = computersCount / 50;

		if (computersCount % 50 != 0) {
			numberOfPages++;
		}

		return numberOfPages;

	}

	private List<Integer> storePagesNumbers(int numberOfPages) {

		List<Integer> numberOfPagesArray = new ArrayList<>();

		for (int i = 0; i < numberOfPages; i++) {
			numberOfPagesArray.add(i + 1);
		}

		return numberOfPagesArray;

	}

}