package com.excilys.cdb.webapp.errors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/internalServerError")
public class InternalServerController {

	protected ModelAndView doGet() {
		return new ModelAndView("/WEB-INF/views/500");
	}

}