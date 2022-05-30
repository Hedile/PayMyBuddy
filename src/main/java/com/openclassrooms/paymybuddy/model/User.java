package com.openclassrooms.paymybuddy.model;

import java.math.BigDecimal;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(nullable = false, name = "firstname")
	private String firstName;

	@Column(nullable = false, name = "lastname")
	private String lastName;
	
	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, name = "solde")
	private double balance;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "connection", joinColumns = {
			@JoinColumn(name = "user_sender_id", referencedColumnName = "user_id", nullable = false) }, 
			inverseJoinColumns = {
			@JoinColumn(name = "user_receiver_id", referencedColumnName = "user_id", nullable = false) })
	private List<User> friends = new ArrayList<>();

	@OneToMany(mappedBy = "accountUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BankAccount> accounts = new ArrayList<>();

	@OneToMany(mappedBy = "sender")
	private List<Transaction> sentTransactions = new ArrayList<>();

	@OneToMany(mappedBy = "receiver")
	private List<Transaction> receiveTransactions = new ArrayList<>();

	
	
	public User() {
		super();
	
	}

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public User(int userId, String firstName, String lastName, String email, String password, double balance,
			List<User> friends, List<BankAccount> accounts, List<Transaction> sentTransactions,
			List<Transaction> receiveTransactions) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.balance = balance;
		this.friends = friends;
		this.accounts = accounts;
		this.sentTransactions = sentTransactions;
		this.receiveTransactions = receiveTransactions;
	}

	public List<BankAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<BankAccount> accounts) {
		this.accounts = accounts;
	}

	public int getUserId() {
		return userId;
	}

	public List<Transaction> getSentTransactions() {
		return sentTransactions;
	}

	public void setSentTransactions(List<Transaction> sentTransactions) {
		this.sentTransactions = sentTransactions;
	}

	public List<Transaction> getReceiveTransactions() {
		return receiveTransactions;
	}

	public void setReceiveTransactions(List<Transaction> receiveTransactions) {
		this.receiveTransactions = receiveTransactions;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
