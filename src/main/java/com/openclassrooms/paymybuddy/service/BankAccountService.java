package com.openclassrooms.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;


@Service
public class BankAccountService {
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	public Iterable<BankAccount> getBankAccounts() {
		return bankAccountRepository.findAll();
	}
	public Optional<BankAccount> getBankAccountById(Integer id) {
		
		return bankAccountRepository.findById(id);
	}	
	
    public void deleteBankAccount(final Integer id) {
    	bankAccountRepository.deleteById(id);
    }

    public BankAccount addBankAccount(BankAccount bankAccount ){
    	BankAccount savedBankAccount= bankAccountRepository.save(bankAccount);
        return savedBankAccount;
    }
}
