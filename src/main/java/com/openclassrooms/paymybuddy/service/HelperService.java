package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.constants.Fees;
import com.openclassrooms.paymybuddy.model.User;

public class HelperService {
	 /**
     * Check if balance is available to execute transaction
     * @param user
     * @param amount
     * @return
     */
    public static double computeTransactionAmountWithFee(User user, double amount) {

		return user.getBalance() - (amount * Fees.transaction_fees);
	}
}
