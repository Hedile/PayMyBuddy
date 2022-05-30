package com.openclassrooms.paymybuddy.model;

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
@Entity
@Table(name = "bank_transaction")
public class BankTransaction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bank_transaction_id")
    private int id;
	
    @Column(name = "description")
    private String description;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private Date date;

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
