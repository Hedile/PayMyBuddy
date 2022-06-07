package com.openclassrooms.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.exceptions.FriendAlreadyLinkedException;
import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;



@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	
	public Iterable<User> getUsers() {
		
		 return userRepository.findAll();      
	       
	}
	public Optional<User> getUserById(int id) {
		
		return userRepository.findById(id);
	}	
	
	public User getUserByEmail (String email) {
        return userRepository.findUserByEmail(email);
    }
	
	 public User saveUser(User user) {
	        User savedUser = userRepository.save(user);
	        return savedUser;
	    }
	 
    public void deleteUserById( Integer id) {
        userRepository.deleteById(id);
    }

    public void addFriend(String email, User currentUser) throws UserNotFoundException, FriendAlreadyLinkedException{
        User newFriend = userRepository.findUserByEmail(email);

        if (newFriend == null){
            throw new UserNotFoundException("User not found with email: " + email);
        }
        List<User> friends= currentUser.getFriends();
        for(User freind : friends) {
        	if( freind.getEmail().equals(email)){
                throw new FriendAlreadyLinkedException("Friend already linked with email: " +email);
            }
        }
        friends.add(newFriend);
        currentUser.setFriends(friends);
       userRepository.save(currentUser);
    }
    
}
