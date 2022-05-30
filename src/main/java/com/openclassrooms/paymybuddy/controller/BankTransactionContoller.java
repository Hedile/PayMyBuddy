package com.openclassrooms.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.openclassrooms.paymybuddy.service.AuthenticationService;
import com.openclassrooms.paymybuddy.service.BankTransactionService;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class BankTransactionContoller {
	@Autowired
	 private  BankTransactionService bankTransactionService;
	@Autowired
	    private AuthenticationService authenticationService;
	@Autowired
	private UserService service;
}
