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

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.mapper.ComputerDTOMapper;
import com.excilys.cdb.persistence.ComputerDao.OrderColumn;
import com.excilys.cdb.persistence.ComputerDao.OrderWay;
import com.excilys.cdb.service.serviceImpl.ComputerServiceImpl;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	ComputerServiceImpl computerServiceImpl = ComputerServiceImpl.INSTANCE;

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int page = 1;
		int size = 50;

		if (StringUtils.isNotBlank(req.getParameter("page"))) {
			page = Integer.parseInt(req.getParameter("page"));
		}
		
		if (StringUtils.isNotBlank(req.getParameter("size"))) {
			size = Integer.parseInt(req.getParameter("size"));
		}

		List<ComputerDTO> computersList;

		String search = "";
		String order = "";

		if (StringUtils.isNotBlank(req.getParameter("search"))) {
			search = req.getParameter("search").trim();
		}

		if (StringUtils.isNotBlank(req.getParameter("order"))) {
			order = req.getParameter("order").trim();
		}

		req.setAttribute("page", page);
		req.setAttribute("size", size);
		req.setAttribute("search", search);
		req.setAttribute("order", order);

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
				.createDTO(computerServiceImpl.getComputers(page, size, search, orderColumn, orderWay).getObjectsList());

		req.setAttribute("computersDTO", computersList);

		int computersCount = computerServiceImpl.countComputers(search);
		req.setAttribute("computersCount", computersCount);

		int numberOfPages = countPages(computersCount, size);

		req.setAttribute("numberOfPages", numberOfPages);
		req.setAttribute("numberOfPagesArray", storePagesNumbers(page, numberOfPages));

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String selectedComputersList = req.getParameter("selection");

		for (String id : selectedComputersList.split(",")) {
			computerServiceImpl.removeComputer(Integer.parseInt(id));
		}

		resp.sendRedirect(this.getServletContext().getContextPath() + "/dashboard");

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
		
		if(page - 2 > 0) {
			numberOfPagesArray.add(page - 2);
			numberOfPagesArray.add(page - 1);
		} else if(page - 1 > 0) {
			numberOfPagesArray.add(page - 1 );
		}
		
		numberOfPagesArray.add(page);
		
		if(page + 2 <= numberOfPages) {
			numberOfPagesArray.add(page + 1);
			numberOfPagesArray.add(page + 2);
		} else if(page + 1 <= numberOfPages) {
			numberOfPagesArray.add(page + 1 );
		}

		return numberOfPagesArray;

	}

}
