package com.openclassrooms.paymybuddy.repository;



import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.User;

@Repository

public interface UserRepository extends CrudRepository<User, Integer>{
	 User findUserByEmail(String email);
	
}
