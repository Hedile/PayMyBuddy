package com.openclassrooms.paymybuddy.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

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
	    public String showHomePage(Model model, HttpServletRequest request) {
	        User user = authenticationService.getCurrentLoggedUser(request);
	        model.addAttribute("user", user);
	        model.addAttribute("friends", user.getFriends());
	      
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
	    public String showAddFriendPage(Model model, HttpServletRequest request) {
	        User user = authenticationService.getCurrentLoggedUser(request);
	        model.addAttribute("friends", user.getFriends());
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
	  
/*@GetMapping("/essai")
	public String getUsers(Model model) {
	    Iterable<User> listUser = service.getUsers();
	    model.addAttribute("users", listUser);
	    return "essai";
	}
	
	
	
	 @GetMapping("/createUser")
		public String createUser(Model model) {
			User u = new User();
			model.addAttribute("user", u);
			return "formNewUser";
		}
	
	 @GetMapping("/deleteUser/{id}")
	 public ModelAndView deleteEmployee(@PathVariable("id") final int id) {
		service.deleteUserById(id);
	     return new ModelAndView("redirect:/");
	 }
	 @PostMapping("/saveEmployee")
	 public ModelAndView saveEmployee(@ModelAttribute User u) {
	    service.saveUser(u);
	     return new ModelAndView("redirect:/");
	 }
	 /***

	
	/* @GetMapping("/home")
		public String home(Model model) {
			Iterable<User> listUser = service.getUsers();
			model.addAttribute("users", listUser);
			return "home";
		}
		
		@GetMapping("/createUser")
		public String createUser(Model model) {
			User u = new User();
			model.addAttribute("user", u);
			return "formNewUser";
		}
		
		@GetMapping("/updateUser/{userId}")
		public String updateUser(@PathVariable("userId") final int id, Model model) {
			Optional<User> u = service.getUserById(id);		
			model.addAttribute("user", u);	
			return "formUpdateUser";		
		}
		
		@GetMapping("/deleteUser/{userId}")
		public ModelAndView deleteEmployee(@PathVariable("userId") final int id) {
			service.deleteUserById(id);
			return new ModelAndView("redirect:/");		
		}
		
		@PostMapping("/saveEmployee")
		public ModelAndView saveUser(@ModelAttribute User u) {
			if(u.getEmail() != null) {
				// Employee from update form has the password field not filled,
				// so we fill it with the current password.
				User current = service.getUserByEmail(u.getEmail());
				
				u.setPassword(current.getPassword());
			}
			service.saveUser(u);
			return new ModelAndView("redirect:/");	
		}
		*/
	    
	  


		  

}
