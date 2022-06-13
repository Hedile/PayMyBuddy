package com.openclassrooms.paymybuddy.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassrooms.paymybuddy.exceptions.InsufficientBalanceException;
import com.openclassrooms.paymybuddy.exceptions.NegativeTransactionAmountException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.AuthenticationService;
import com.openclassrooms.paymybuddy.service.BankAccountService;
import com.openclassrooms.paymybuddy.service.TransactionService;
import com.openclassrooms.paymybuddy.service.UserService;

@Controller
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private AuthenticationService authenticationService;

	@GetMapping(value = "/transaction")
	public String transactionPage(Model model, HttpServletRequest request,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "3") int size) {
		User user = authenticationService.getCurrentLoggedUser(request);
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<Transaction> transactionPage = transactionService.findPaginatedTransaction(pageable, user);
		List<Transaction> transactionList = transactionPage.getContent();
		int totalPages = transactionPage.getTotalPages();
		System.out.println(transactionPage.getSize());
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("user", user);
		model.addAttribute("username", user.getFullName());
		model.addAttribute("balance", user.getBalance());
		model.addAttribute("friends", user.getFriends());
		model.addAttribute("accounts", user.getAccounts());
		model.addAttribute("transactionPage", transactionPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("transactionList", transactionList);

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
