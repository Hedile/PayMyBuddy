package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.BankAccount;


@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Integer>{
	@Query(value = "SELECT *FROM bank_account WHERE user_id = :user_id ", nativeQuery = true)
	public List<BankAccount> findBankAccountByUser(@Param("user_id") Integer id);
	

}
