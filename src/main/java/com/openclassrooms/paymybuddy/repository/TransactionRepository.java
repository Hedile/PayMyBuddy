package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.Transaction;



@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
	
	@Query(value = "SELECT * FROM transaction  WHERE user_sender_id = :user_id OR user_receiver_id = :user_id", nativeQuery = true)
	public List<Transaction> findTransactionsByUser(@Param("user_id") Integer id);
	/*@Query(value = "SELECT * FROM transaction JOIN connection ON transaction.connection_id= connection.connection_id WHERE connection.user_sender_id = :user_id OR connection.user_receiver_id = :user_id", nativeQuery = true)
	public List<Transaction> findTransactionsByUser(@Param("user_id") Integer id);*/
}
