package com.openclassrooms.paymybuddy.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.constants.Fees;
import com.openclassrooms.paymybuddy.constants.TransactionType;
import com.openclassrooms.paymybuddy.exceptions.AmountFormatException;
import com.openclassrooms.paymybuddy.exceptions.InsufficientBalanceException;
import com.openclassrooms.paymybuddy.exceptions.NegativeTransactionAmountException;

import com.openclassrooms.paymybuddy.model.Transaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.TransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private UserRepository userRepository;

	public Iterable<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}

	public Optional<Transaction> getTransactionById(Integer id) {

		return transactionRepository.findById(id);
	}

	public List<Transaction> getTransactionsByUser(User user) {
		return transactionRepository.findTransactionsByUser(user.getUserId());
	}

	
	public void deleteTransaction(final Integer id) {
		transactionRepository.deleteById(id);
	}

	public Transaction addTransaction(Transaction transaction) {
		Transaction savedTransaction = transactionRepository.save(transaction);
		return savedTransaction;
	}
	

    public Page<Transaction> findPaginatedTransaction(Pageable pageable, User user) {
        int pageSize = pageable.getPageSize();
   
        int currentPage = pageable.getPageNumber();
      
        int startItem = currentPage * pageSize;
       
        List<Transaction> list;
        List<Transaction> transactions=transactionRepository.findTransactionsByUser(user.getUserId());;
        if (transactions.size() < startItem) {
        	list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, transactions.size());
            list = transactions.subList(startItem, toIndex);
        }

        Page<Transaction> transactionPage
          = new PageImpl<Transaction>(list, PageRequest.of(currentPage, pageSize), transactions.size());

        return transactionPage;
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
	public void sendToFriend(String email, User user, String amount, String description)
			throws NegativeTransactionAmountException, InsufficientBalanceException, AmountFormatException {
		User userFriend = new User();
	
		List<User> friends = user.getFriends();
		for (User friend : friends) {
			if (friend.getEmail().equals(email)) {
				userFriend = friend;
			}
		}
	
		 if (!isNumeric(amount)){
	            throw new AmountFormatException("The amount must be a number.");
	        }

	        double montant = Double.parseDouble(amount);
	        	
		if (montant <= 0) {
			throw new NegativeTransactionAmountException("The amount cannot be negative or equals to 0.");
		}

		if (HelperService.computeTransactionAmountWithFee(user, montant) < 0) {
			throw new InsufficientBalanceException("the account balance is insufficient: " + user.getBalance());
		}

		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setDate(LocalDateTime.now());
		transaction.setType(TransactionType.SEND_TO_FRIEND);
		transaction.setAmount(montant);
		transaction.setReceiver(userFriend);
		transaction.setSender(user);

		user.setBalance(HelperService.computeTransactionAmountWithFee(user, montant));
		userRepository.save(user);
		userFriend.setBalance(userFriend.getBalance() + montant);
		userRepository.save(userFriend);
		transactionRepository.save(transaction);
	}
}
