package com.openclassrooms.paymybuddy.controller;



import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassrooms.paymybuddy.exceptions.FriendAlreadyLinkedException;
import com.openclassrooms.paymybuddy.exceptions.UserAlreadyCreatedException;
import com.openclassrooms.paymybuddy.exceptions.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.AuthenticationService;
import com.openclassrooms.paymybuddy.service.UserService;





@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	@Autowired
	 private  AuthenticationService authenticationService;

	
	
	   @GetMapping("/")
	    public String showHomePage(Model model, HttpServletRequest request,@RequestParam(name = "page", defaultValue = "1") int page,
				@RequestParam(name = "size", defaultValue = "3") int size) {
	        User user = authenticationService.getCurrentLoggedUser(request);
	        Pageable pageable = PageRequest.of(page - 1, size);
			Page<User> friendPage = service.findPaginatedFriends(pageable, user);
			List<User> friendList = friendPage.getContent();
			int totalPages = friendPage.getTotalPages();
			
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			model.addAttribute("friendPage", friendPage);
			model.addAttribute("totalPages", totalPages);
	        model.addAttribute("friends", friendList);
	        model.addAttribute("user", user);
	      
	      
	        return "homePage";

	    }

	    @GetMapping(value = "/profile")
	    public String showProfilePage(Model model, HttpServletRequest request) {
	        User user = authenticationService.getCurrentLoggedUser(request);
	        model.addAttribute("user", user);
	        model.addAttribute("accounts", user.getAccounts());
	        return "profile";
	    }

	    @GetMapping(value = "/contact")
	    public String showAddFriendPage(Model model, HttpServletRequest request,@RequestParam(name = "page", defaultValue = "1") int page,
				@RequestParam(name = "size", defaultValue = "3") int size)  {
	        User user = authenticationService.getCurrentLoggedUser(request);
	        Pageable pageable = PageRequest.of(page - 1, size);
			Page<User> friendPage = service.findPaginatedFriends(pageable, user);
			List<User> friendList = friendPage.getContent();
			int totalPages = friendPage.getTotalPages();
			
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			model.addAttribute("friendPage", friendPage);
			model.addAttribute("totalPages", totalPages);
	        model.addAttribute("friends", friendList);
	        model.addAttribute("username", user.getFullName());
	        return "contact";
	    }

	    @GetMapping("/contact/new")
	    public String addFriend(@RequestParam(name= "friendEmail") String email, HttpServletRequest request, RedirectAttributes redirectAttributes){
	        User user = authenticationService.getCurrentLoggedUser(request);
	      
	        try {
	            service.addFriend(email, user);
	            
	        } catch (UserNotFoundException  e) {
	            redirectAttributes.addAttribute("error", e.getMessage());
	        }  catch (FriendAlreadyLinkedException e) {
	            redirectAttributes.addAttribute("error", e.getMessage());
	        }
	        return"redirect:/contact";
	    }
	  

	  
		  

}
