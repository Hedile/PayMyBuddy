package com.openclassrooms.paymybuddy.exceptions;

public class InsufficientBalanceException extends Exception{
	public InsufficientBalanceException(String message) {
        super(message);
    }
}
