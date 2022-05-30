package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.BankAccount;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Integer>{

}
