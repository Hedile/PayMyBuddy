package com.openclassrooms.paymybuddy.exceptions;

public class NegativeTransactionAmountException  extends Exception {
	 public NegativeTransactionAmountException(String message) {
	        super(message);
	    }
}
