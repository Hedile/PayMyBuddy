package com.openclassrooms.paymybuddy.exceptions;

public class UserAlreadyCreatedException extends Exception{
	   public UserAlreadyCreatedException(String errorMessage) {
	        super(errorMessage);
	    }
}
