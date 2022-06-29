package com.openclassrooms.paymybuddy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassrooms.paymybuddy.exceptions.UserAlreadyCreatedException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.AuthenticationService;

@Controller
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;

	@GetMapping("/login")
	public String showLoginPage() {

		return "login";

	}

	@GetMapping("/register")
	public String showRegisterPage(Model model) {

		model.addAttribute("user", new User());
		return "register";

	}

	@PostMapping("/user/register")
	public String registerUser(User user, RedirectAttributes redirectAttributes) throws Exception {
		try {
			authenticationService.registerUser(user);
		} catch (UserAlreadyCreatedException e) {
			redirectAttributes.addAttribute("account_already_exists", true);
			return "redirect:/register";
		}
		return "redirect:/";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		authenticationService.logout(request, response);
		return "redirect:/login";
	}

}
