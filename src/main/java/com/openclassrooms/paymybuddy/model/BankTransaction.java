package com.openclassrooms.paymybuddy.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.openclassrooms.paymybuddy.constants.TransactionType;
@Entity
@Table(name = "bank_transaction")
public class BankTransaction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bank_transaction_id")
    private int id;
	
   
    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime Date;
 
    @Column(name = "type")
    private TransactionType type;
    @ManyToOne(

			cascade = { 
					CascadeType.PERSIST, 
					CascadeType.MERGE 
					} 
                                      )
	@JoinColumn(name="bank_account_id")
	private BankAccount account;

	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	

	public LocalDateTime getDate() {
		return Date;
	}

	public void setDate(LocalDateTime date) {
		Date = date;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType addToInternalAccount) {
		this.type = addToInternalAccount;
	}

}
