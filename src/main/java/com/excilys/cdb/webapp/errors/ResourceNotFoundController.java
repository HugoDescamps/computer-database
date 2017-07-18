package com.excilys.cdb.webapp.errors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/resourceNotFound")
public class ResourceNotFoundController {

	@GetMapping
	protected ModelAndView doGet() {

		return new ModelAndView("/WEB-INF/views/404");
	}

}