package com.openclassrooms.paymybuddy.controller;

import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.AuthenticationService;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;



@Controller
public class TransactionController {
	@Autowired
	 private  TransactionService transactionService;
	@Autowired
	    private AuthenticationService authenticationService;
	@Autowired
	private UserService service;

	    
	    @GetMapping(value = "/transaction")
	    public String transactionPage(Model model, HttpServletRequest request) {
	        User user = authenticationService.getCurrentLoggedUser(request);
	        List<Transaction> transactions = transactionService.getTransactionsByUser(user);
	    
	        model.addAttribute("transactions", transactions);
	        return "transaction";
	    }

	 

	   

}
