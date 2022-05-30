package com.openclassrooms.paymybuddy.model;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
    private int id;

    @ManyToOne
    @JoinColumn(name="connection_id",insertable=false, updatable=false)
    private User sender;

    @ManyToOne
    @JoinColumn(name="connection_id",insertable=false, updatable=false)
    private User receiver;
    
    @Column(name = "description")
    private String description;
 
    @Column(nullable = false)
    private double amount;
    
  
    @Column(nullable = false)
    private Date date;
 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
    
}
