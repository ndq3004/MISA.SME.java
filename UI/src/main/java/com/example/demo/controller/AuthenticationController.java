package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController {



	@RequestMapping(value = { "/login", "/" }, method = RequestMethod.GET)
	public String login() {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("Views/login"); // resources/template/login.html
		return "/login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
//		ModelAndView modelAndView = new ModelAndView();
//		User user = new User();
//		modelAndView.addObject("user", user);
//		modelAndView.setViewName("/Views/register"); // resources/template/register.html
		return "/register";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("/Views/home"); // resources/template/home.html
		return "/home";
	}
	@RequestMapping(value = "/rae", method = RequestMethod.GET)
	public String rae() {
		return "/rae";
	}

	
}
