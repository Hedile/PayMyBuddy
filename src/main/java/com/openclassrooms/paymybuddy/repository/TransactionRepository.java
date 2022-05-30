package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
	
	
	List<Transaction> findTransactionsBySender(User user);
}
