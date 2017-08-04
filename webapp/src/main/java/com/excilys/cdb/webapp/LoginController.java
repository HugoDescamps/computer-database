package com.excilys.cdb.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * SpringSecurity authentification
 * credentialsError & logout parameters display a message
 * @author Hugo Descamps
 */

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping
	protected ModelAndView doGet(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView modelAndView = new ModelAndView();

		if (error != null) {
			modelAndView.addObject("error", "login.credentialsError");
		}

		if (logout != null) {
			modelAndView.addObject("msg", "login.logout");
		}

		modelAndView.setViewName("/WEB-INF/views/login");

		return modelAndView;
	}

}
