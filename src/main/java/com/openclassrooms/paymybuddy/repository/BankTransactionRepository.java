package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;


@Repository
public interface BankTransactionRepository extends CrudRepository<BankTransaction, Integer>{
	List<BankTransaction> findBankTransactionsByAccount(BankAccount account);
}
