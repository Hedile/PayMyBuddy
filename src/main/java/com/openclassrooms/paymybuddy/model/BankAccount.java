package com.openclassrooms.paymybuddy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bank_account_id")
	private int bankAccountId;
	
	@Column(name = "iban", nullable = false)
	private String iban;
	
	 @OneToMany(mappedBy="account",
			 cascade = { 
						CascadeType.PERSIST, 
						CascadeType.MERGE 
						} , 
  		   orphanRemoval = true
  		   )
  		   
	private List<BankTransaction> bankTransactions = new ArrayList<>();
	 
	@ManyToOne(cascade = { 
					CascadeType.PERSIST, 
					CascadeType.MERGE 
					} 
                                      )
	@JoinColumn(name="user_id")
	private User accountUser;
	
	public BankAccount() {
		super();
	
	}
	public BankAccount(int bankAccountId, String iban) {
		super();
		this.bankAccountId = bankAccountId;
		this.iban = iban;
	}
	public List<BankTransaction> getBankTransactions() {
		return bankTransactions;
	}
	public void setBankTransactions(List<BankTransaction> bankTransactions) {
		this.bankTransactions = bankTransactions;
	}
	public User getAccountUser() {
		return accountUser;
}
	public void setAccountUser(User accountUser) {
		this.accountUser = accountUser;
}
	public int getBankAccountId() {
		return bankAccountId;
	}
	public void setBankAccountId(int bankAccountId) {
		this.bankAccountId = bankAccountId;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	
}
