package com.openclassrooms.paymybuddy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassrooms.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.openclassrooms.paymybuddy.exceptions.FriendAlreadyLinkedException;
import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.AuthenticationService;
import com.openclassrooms.paymybuddy.service.BankAccountService;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class BankAccountController {
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	 private  AuthenticationService authenticationService;
	
	 @GetMapping("/profile/account/new")
	    public String addFriend(@RequestParam(name= "iban") String iban, HttpServletRequest request, RedirectAttributes redirectAttributes){
	        User user = authenticationService.getCurrentLoggedUser(request);
	      
	        try {
	        	bankAccountService.addBankAccount(iban, user);
	        }  catch (AccountAlreadyExistsException e) {
	            redirectAttributes.addAttribute("error", e.getMessage());
	        }
	        return"redirect:/profile";
	    }

}
