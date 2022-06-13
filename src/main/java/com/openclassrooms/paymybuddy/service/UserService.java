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

import com.openclassrooms.paymybuddy.exceptions.FriendAlreadyLinkedException;
import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.Transaction;
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
    public Page<User> findPaginatedFriends(Pageable pageable, User user) {
        int pageSize = pageable.getPageSize();
        System.out.println(pageSize);
        int currentPage = pageable.getPageNumber();
        System.out.println(currentPage);
        int startItem = currentPage * pageSize;
        System.out.println(startItem);
        List<User> list;
        List<User> friends=user.getFriends();
        if (friends.size() < startItem) {
        	list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, friends.size());
            list = friends.subList(startItem, toIndex);
        }

        Page<User> friendsPage
          = new PageImpl<User>(list, PageRequest.of(currentPage, pageSize), friends.size());

        return friendsPage;
    }
    public void addFriend(String email, User user) throws UserNotFoundException, FriendAlreadyLinkedException{
        User newFriend = userRepository.findUserByEmail(email);
        User currentUser=userRepository.findUserByEmail(user.getEmail());
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
