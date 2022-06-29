package com.openclassrooms.paymybuddy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassrooms.paymybuddy.exceptions.AmountFormatException;
import com.openclassrooms.paymybuddy.exceptions.InsufficientBalanceException;
import com.openclassrooms.paymybuddy.exceptions.NegativeTransactionAmountException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.AuthenticationService;
import com.openclassrooms.paymybuddy.service.BankTransactionService;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;


@Controller
public class BankTransactionContoller {
	@Autowired
	private BankTransactionService bankTransactionService;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private UserService service;

	
	
	@PostMapping("/transaction/credit")
	public String addMoneyToInternalAccount(@RequestParam(name = "iban")String iban,
			@RequestParam(name = "amount") String amount,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User user = authenticationService.getCurrentLoggedUser(request);
		
		try {
			bankTransactionService.CreditInternalAccount(iban, amount, user);
		} catch (NegativeTransactionAmountException |  AmountFormatException  e) {
			redirectAttributes.addAttribute("error_new_external_transaction", e.getMessage());
		}

		return "redirect:/transaction";
	}
	
	@PostMapping("/transaction/debit")
	public String debitMoneyToInternalAccount(@RequestParam(name = "iban")String iban,
			@RequestParam(name = "amount") String amount,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User user = authenticationService.getCurrentLoggedUser(request);
		
		try {
			bankTransactionService.DebitInternalAccount(iban, amount, user);
		} catch (NegativeTransactionAmountException|InsufficientBalanceException |  AmountFormatException e) {
			redirectAttributes.addAttribute("error_new_external_transaction", e.getMessage());
		}

		return "redirect:/transaction";
	}
}
