package com.openclassrooms.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;


@Service
public class BankTransactionService {
	@Autowired
	private BankTransactionRepository bankTransactionRepository;
	
	public Iterable<BankTransaction> getBankTransactions() {
		return bankTransactionRepository.findAll();
	}
	public Optional<BankTransaction> getBankTransactionById(Integer id) {
		
		return bankTransactionRepository.findById(id);
	}	
	public List<BankTransaction> getBankTransactionsByAccount (BankAccount account) {
        return bankTransactionRepository.findBankTransactionsByAccount(account);
    }
    public void deleteBankTransaction(final Integer id) {
    	bankTransactionRepository.deleteById(id);
    }

    public BankTransaction addBankTransaction(BankTransaction bankTransaction ){
    	BankTransaction savedBankTransaction= bankTransactionRepository.save(bankTransaction);
        return savedBankTransaction;
    }
}
