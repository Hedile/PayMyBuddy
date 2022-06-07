package com.openclassrooms.paymybuddy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.constants.TransactionType;
import com.openclassrooms.paymybuddy.exceptions.InsufficientBalanceException;
import com.openclassrooms.paymybuddy.exceptions.NegativeTransactionAmountException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@Service
public class BankTransactionService {
	@Autowired
	private BankTransactionRepository bankTransactionRepository;

	@Autowired
	private UserRepository userRepository;

	public Iterable<BankTransaction> getBankTransactions() {
		return bankTransactionRepository.findAll();
	}

	public Optional<BankTransaction> getBankTransactionById(Integer id) {

		return bankTransactionRepository.findById(id);
	}

	public List<BankTransaction> getBankTransactionsByAccount(BankAccount account) {
		return bankTransactionRepository.findBankTransactionsByAccount(account);
	}

	public void deleteBankTransaction(final Integer id) {
		bankTransactionRepository.deleteById(id);
	}

	public BankTransaction addBankTransaction(BankTransaction bankTransaction) {
		BankTransaction savedBankTransaction = bankTransactionRepository.save(bankTransaction);
		return savedBankTransaction;
	}

	public void CreditInternalAccount(String iban, double amount, User user)
			throws NegativeTransactionAmountException {
		BankAccount currentAccount = new BankAccount();
		List<BankAccount> accounts = user.getAccounts();
		for (BankAccount account : accounts) {
			if (account.getIban().equals(iban)) {
				currentAccount = account;
			}
		}

		if (amount <= 0) {
			throw new NegativeTransactionAmountException("The amount cannot be negative or equals to 0.");
		}

		BankTransaction bankTransaction = new BankTransaction();
		bankTransaction.setDate(LocalDateTime.now());
		bankTransaction.setAccount(currentAccount);
		bankTransaction.setType(TransactionType.CREDIT_INTERNAL_ACCOUNT);
		bankTransaction.setAmount(amount);
		bankTransactionRepository.save(bankTransaction);

		user.setBalance(user.getBalance() + bankTransaction.getAmount() * 0.995);
		userRepository.save(user);
	}

	public void DebitInternalAccount(String iban, double amount, User user)
			throws NegativeTransactionAmountException, InsufficientBalanceException {
		BankAccount currentAccount = new BankAccount();
		List<BankAccount> accounts = user.getAccounts();
		for (BankAccount account : accounts) {
			if (account.getIban().equals(iban)) {
				currentAccount = account;
			}
		}
		if (amount <= 0) {
			throw new NegativeTransactionAmountException("The amount cannot be negative or equals to 0.");
		}

		if (HelperService.computeTransactionAmountWithFee(user, amount) < 0) {
			throw new InsufficientBalanceException("the account balance is insufficient: " + user.getBalance());
		}

		BankTransaction bankTransaction = new BankTransaction();
		bankTransaction.setDate(LocalDateTime.now());
		bankTransaction.setAccount(currentAccount);
		bankTransaction.setType(TransactionType.DEBIT_INTERNAL_ACCOUNT);
		bankTransaction.setAmount(amount);
		bankTransactionRepository.save(bankTransaction);

		user.setBalance(HelperService.computeTransactionAmountWithFee(user, amount));
		userRepository.save(user);
	}
}
