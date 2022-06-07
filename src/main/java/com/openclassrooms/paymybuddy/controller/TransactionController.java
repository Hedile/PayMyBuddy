package com.openclassrooms.paymybuddy.controller;

import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassrooms.paymybuddy.exceptions.InsufficientBalanceException;
import com.openclassrooms.paymybuddy.exceptions.NegativeTransactionAmountException;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.AuthenticationService;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private UserService service;

	@GetMapping(value = "/transaction")
	public String transactionPage(Model model, HttpServletRequest request) {
		User user = authenticationService.getCurrentLoggedUser(request);
		List<Transaction> transactions = transactionService.getTransactionsByUser(user);
		model.addAttribute("user", user);
		model.addAttribute("username", user.getFullName());
		model.addAttribute("balance", user.getBalance());
		model.addAttribute("friends", user.getFriends());
		model.addAttribute("accounts", user.getAccounts());
		model.addAttribute("transactions", transactions);
		return "transaction";
	}

	@PostMapping("/transaction/friend/new")
	public String sendToFriend(@RequestParam(name = "friendEmail") String email,
			@RequestParam(name = "amount") double amount, @RequestParam(name = "description") String descrip,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		User user = authenticationService.getCurrentLoggedUser(request);
		try {
			transactionService.sendToFriend(email, user, amount, descrip);
		} catch (NegativeTransactionAmountException | InsufficientBalanceException e) {
			redirectAttributes.addAttribute("error_send_to_friend", e.getMessage());
		}

		return "redirect:/transaction";
	}

}
