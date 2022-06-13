package com.openclassrooms.paymybuddy.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.exceptions.AccountAlreadyExistsException;
import com.openclassrooms.paymybuddy.exceptions.FriendAlreadyLinkedException;
import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
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
    public Page<BankAccount> findPaginatedBankAccount(Pageable pageable, User user) {
        int pageSize = pageable.getPageSize();
        System.out.println(pageSize);
        int currentPage = pageable.getPageNumber();
        System.out.println(currentPage);
        int startItem = currentPage * pageSize;
        System.out.println(startItem);
        List<BankAccount >list;
        List<BankAccount> bankAccounts=user.getAccounts();
        if (bankAccounts.size() < startItem) {
        	list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, bankAccounts.size());
            list = bankAccounts.subList(startItem, toIndex);
        }

        Page<BankAccount> bankAccountPage
          = new PageImpl<BankAccount>(list, PageRequest.of(currentPage, pageSize), bankAccounts.size());

        return bankAccountPage;
    }
   /* public BankAccount addBankAccount(BankAccount bankAccount ){
    	BankAccount savedBankAccount= bankAccountRepository.save(bankAccount);
        return savedBankAccount;
    }*/
    public void addBankAccount(String iban, User currentUser) throws AccountAlreadyExistsException {
    	BankAccount newAccount= new BankAccount();
    			
    	List<BankAccount> accounts = currentUser.getAccounts();
		for (BankAccount account : accounts) {
			if (account.getIban().equals(iban)) {
				 throw new AccountAlreadyExistsException("Account already exists with iban: " +iban);
			}
		}
       newAccount.setIban(iban);
       newAccount.setAccountUser(currentUser);
        accounts.add(newAccount);
       
        bankAccountRepository.save(newAccount);
    }
}
