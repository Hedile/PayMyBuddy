package com.openclassrooms.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;


@Service
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	
	public Iterable<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}
	public Optional<Transaction> getTransactionById(Integer id) {
		
		return transactionRepository.findById(id);
	}	
	public List<Transaction> getTransactionsByUser (User user) {
        return transactionRepository.findTransactionsBySender(user);
    }
	
    public void deleteTransaction(final Integer id) {
    	transactionRepository.deleteById(id);
    }

    public Transaction addTransaction(Transaction transaction ){
    	Transaction savedTransaction = transactionRepository.save(transaction);
        return savedTransaction;
    }
}
