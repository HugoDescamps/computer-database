package com.excilys.cdb.webapp.errorPages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/accessDenied")
public class AccessDeniedController {

	@GetMapping
	protected ModelAndView doGet() {
		return new ModelAndView("/WEB-INF/views/403");
	}

}
