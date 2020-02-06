package com.sdpaymentgateway.model;

import java.util.List;

import com.sdpaymentgateway.exception.ExceptionDetail;

public class ConfirmPaymentTransaction  {
	
	private PaymentTransaction paymentTransaction;
	private List<ExceptionDetail> exceptionDetails;
	private String port;

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public List<ExceptionDetail> getExceptionDetails() {
		return exceptionDetails;
	}

	public void setExceptionDetails(List<ExceptionDetail> exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	} 
	
	public ConfirmPaymentTransaction(PaymentTransaction paymentTransaction, List<ExceptionDetail> exceptionDetails, String port) {
		super();
		this.paymentTransaction = paymentTransaction;
		this.exceptionDetails = exceptionDetails;
		this.port = port;
	}

	public ConfirmPaymentTransaction(){ 
		super();
	}

	public PaymentTransaction getPaymentTransaction() {
		return paymentTransaction;
	}

	public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
		this.paymentTransaction = paymentTransaction;
	}
	
}
