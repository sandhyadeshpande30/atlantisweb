package com.sdpaymentgateway.service;


import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdpaymentgateway.model.ConfirmPaymentTransaction;
import com.sdpaymentgateway.model.PaymentTransaction;
import com.sdpaymentgateway.model.Query;


@Service
public interface PaymentGatewayService {
	
	/**
	 * Get transaction details for requested transaction
	 * @param transactionId
	 * @return
	 */
	Resource<PaymentTransaction> getTransactionDetails(Integer transactionId);
	
	/**
	 * Get details for all PaymentTransactions
	 * @return list of transactions 
	 */
	List<PaymentTransaction> findTransactions(Query query);
	
	/**
	 * Perform a payment transaction 
	 * @return the transaction with the details
	 */
	ConfirmPaymentTransaction pay(PaymentTransaction transactionDetails);
	


}
