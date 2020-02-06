package tth.loyalty.program.model;

import java.math.BigDecimal;
import java.util.Date; 

public class PaymentTransaction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaymentTransaction(){		
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
	 
	public String getFrom() {
		return sentFrom;
	}
	public void setFrom(String from) {
		this.sentFrom = from;
	}
	 
	public String getTo() {
		return sentTo;
	}
	public void setTo(String to) {
		this.sentTo = to;
	}
	 
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
	 
	private Integer transactionId;
	 
	private Date transactionDate;
	 
	private String sentFrom;
	
	 private String sentTo;
	
	 private BigDecimal amount;
	
	 private String remarks;
}
