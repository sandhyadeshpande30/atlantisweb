package com.sdpaymentgateway.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

//import io.swagger.annotations.ApiModelProperty;

//@ApiModel(description="All details of the transaction")
@Entity 
public class PaymentTransaction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaymentTransaction(){		
	}
		
	public PaymentTransaction(Integer transactionId, Date sentDate, String sentFrom, String sentTo, BigDecimal amount, String remarks) {
		super();
		this.transactionId = transactionId;
		this.transactionDate = sentDate;
		this.sentFrom = sentFrom;
		this.sentTo = sentTo;
		this.amount = amount;
		this.remarks = remarks;
	}
	public Integer getId() {
		return transactionId;
	}
	public void setId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public Date getTransactionTime() {
		return transactionDate;
	}
	public void setTransactionTime(Date transactionTime) {
		this.transactionDate = transactionTime;
	}
	
	@NotNull
	public String getFrom() {
		return sentFrom;
	}
	public void setFrom(String from) {
		this.sentFrom = from;
	}
	
	@NotNull	
	public String getTo() {
		return sentTo;
	}
	public void setTo(String to) {
		this.sentTo = to;
	}
	
	@NotNull	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Id
	@GeneratedValue
	private Integer transactionId;
	 
	private Date transactionDate;
	
	@Size(min=2, max=100)
	@NotBlank
	@NotNull
	private String sentFrom;
	
	//@ApiModelProperty(notes="Card number supported as of now")
	@Size(min=2,max=100)
	@NotBlank
	@NotNull
	private String sentTo;
	
	@NotNull
	@Positive
	@Min(1)
	private BigDecimal amount;
	
	@Pattern(regexp = "^[a-zA-Z\\s0-9]+$")	
	private String remarks;
}
