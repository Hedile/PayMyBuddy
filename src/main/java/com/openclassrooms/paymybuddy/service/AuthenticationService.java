package com.openclassrooms.paymybuddy.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import com.openclassrooms.paymybuddy.exceptions.UserAlreadyCreatedException;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
@Service
public class AuthenticationService {
	 @Autowired
	 private UserRepository userRepository;
	 @Autowired
	    private  PasswordEncoder passwordEncoder;
	
	    private SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();


	    public AuthenticationService() {
			super();
			
		}


		public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
				SecurityContextLogoutHandler securityContextLogoutHandler) {
			super();
			this.userRepository = userRepository;
			this.passwordEncoder = passwordEncoder;
			this.securityContextLogoutHandler = securityContextLogoutHandler;
		}


		public User registerUser(User user) throws UserAlreadyCreatedException {
	        User alreadyRegisteredUser = userRepository.findUserByEmail(user.getEmail());
	        if (alreadyRegisteredUser != null) {
	            throw new UserAlreadyCreatedException("User already created");
	        }
	        String hashedPassword = passwordEncoder.encode(user.getPassword());
	        user.setPassword(hashedPassword);
	        return userRepository.save(user);
	    }

	
	    public void logout(HttpServletRequest request, HttpServletResponse response) {
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null) {
	            securityContextLogoutHandler.logout(request, response, auth);
	        }
	    }
	    public User getLoggedUser(HttpServletRequest request) {
	    	HttpSession httpSession= request.getSession();
	    	SecurityContext securityContext=(SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
	    	 System.out.println(securityContext);
	    	String email=securityContext.getAuthentication().getName();
	    	 System.out.println("Email: " + email);
	        return userRepository.findUserByEmail(email);
	    }
	    public User getCurrentLoggedUser(HttpServletRequest request) {
	        return userRepository.findUserByEmail(request.getUserPrincipal().getName());
	    }

}
