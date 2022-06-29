package com.openclassrooms.paymybuddy.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.constants.TransactionType;
import com.openclassrooms.paymybuddy.exceptions.AmountFormatException;
import com.openclassrooms.paymybuddy.exceptions.InsufficientBalanceException;
import com.openclassrooms.paymybuddy.exceptions.NegativeTransactionAmountException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.Transaction;
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
	 public static boolean isNumeric(String strNum) {
	        if (strNum == null) {
	            return false;
	        }
	        try {
	            double d = Double.parseDouble(strNum);
	        } catch (NumberFormatException nfe) {
	            return false;
	        }
	        return true;
	    }
	 
	public void CreditInternalAccount(String iban, String amount, User user)
			throws NegativeTransactionAmountException, AmountFormatException {
		BankAccount currentAccount = new BankAccount();
		List<BankAccount> accounts = user.getAccounts();
		for (BankAccount account : accounts) {
			if (account.getIban().equals(iban)) {
				currentAccount = account;
			}
		}
		 if (!isNumeric(amount)){
	            throw new AmountFormatException("The amount must be a number.");
	        }

	        double montant = Double.parseDouble(amount);
		if (montant <= 0) {
			throw new NegativeTransactionAmountException("The amount cannot be negative or equals to 0.");
		}

		BankTransaction bankTransaction = new BankTransaction();
		bankTransaction.setDate(LocalDateTime.now());
		bankTransaction.setAccount(currentAccount);
		bankTransaction.setType(TransactionType.CREDIT_INTERNAL_ACCOUNT);
		bankTransaction.setAmount(montant);
		bankTransactionRepository.save(bankTransaction);

		user.setBalance(user.getBalance() + bankTransaction.getAmount() * 0.995);
		userRepository.save(user);
	}

	public void DebitInternalAccount(String iban, String amount, User user)
			throws NegativeTransactionAmountException, InsufficientBalanceException, AmountFormatException {
		BankAccount currentAccount = new BankAccount();
		List<BankAccount> accounts = user.getAccounts();
		for (BankAccount account : accounts) {
			if (account.getIban().equals(iban)) {
				currentAccount = account;
			}
		}
		 if (!isNumeric(amount)){
	            throw new AmountFormatException("The amount must be a number.");
	        }

	        double montant = Double.parseDouble(amount);
		if (montant <= 0) {
			throw new NegativeTransactionAmountException("The amount cannot be negative or equals to 0.");
		}

		if (HelperService.computeTransactionAmountWithFee(user, montant) < 0) {
			throw new InsufficientBalanceException("the account balance is insufficient: " + user.getBalance());
		}

		BankTransaction bankTransaction = new BankTransaction();
		bankTransaction.setDate(LocalDateTime.now());
		bankTransaction.setAccount(currentAccount);
		bankTransaction.setType(TransactionType.DEBIT_INTERNAL_ACCOUNT);
		bankTransaction.setAmount(montant);
		bankTransactionRepository.save(bankTransaction);

		user.setBalance(HelperService.computeTransactionAmountWithFee(user, montant));
		userRepository.save(user);
	}
}
