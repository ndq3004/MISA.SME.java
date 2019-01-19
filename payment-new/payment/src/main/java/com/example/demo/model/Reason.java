package com.example.demo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Reason")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Reason {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reasonTypeID;
	public String getDescriptionReason() {
		return descriptionReason;
	}

	public void setDescriptionReason(String descriptionReason) {
		this.descriptionReason = descriptionReason;
	}

	public void setPayments(Set<PaymentReceipt> payments) {
		this.payments = payments;
	}

	private String journalMemo;
	@Column(name = "description")
	private String descriptionReason;
	@OneToMany(mappedBy = "reason", fetch = FetchType.LAZY)
	private Set<PaymentReceipt> payments;

	public Reason(int reasonTypeID, String journalMemo) {
		super();
		this.reasonTypeID = reasonTypeID;
		this.journalMemo = journalMemo;
		
	}

	public int getReasonTypeID() {
		return reasonTypeID;
	}

	public void setReasonTypeID(int reasonTypeID) {
		this.reasonTypeID = reasonTypeID;
	}

	public String getJournalMemo() {
		return journalMemo;
	}

	public void setJournalMemo(String journalMemo) {
		this.journalMemo = journalMemo;
	}

	public Reason() {
		super();
	}

}
