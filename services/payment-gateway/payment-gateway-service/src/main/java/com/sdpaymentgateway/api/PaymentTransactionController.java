package com.sdpaymentgateway.api;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdpaymentgateway.model.ConfirmPaymentTransaction;
import com.sdpaymentgateway.model.PaymentTransaction;
import com.sdpaymentgateway.service.PaymentGatewayService; 

@RestController
public class PaymentTransactionController {
		
	@Autowired
	private PaymentGatewayService service;
	
	private static final Logger log = LoggerFactory.getLogger(PaymentTransactionController.class);

	
	
		@GetMapping(path="/transactions",produces = "application/json; charset=UTF-8")
		@Valid
		@CrossOrigin(allowedHeaders="*")		
		public List<PaymentTransaction> retrieveAllTransactions(){
			log.info("retreiving all transactions for now");
			//TODO map the query
			return service.findTransactions(null);
		}	
		 
		@GetMapping(path="/transactions/{transactionId}",produces = "application/json; charset=UTF-8")
		@Valid
		@CrossOrigin(allowedHeaders="*")		
		public Resource<PaymentTransaction> retrieveTransactionById(@Valid @PathVariable Integer transactionId ){
			log.info("retrieveTransactionById:"+transactionId);			 
			return service.getTransactionDetails(transactionId);
		} 
		
		@PostMapping(path="/transactions",produces = "application/json; charset=UTF-8", consumes = "application/json; charset=UTF-8")
		@Valid
		@CrossOrigin(allowedHeaders="*")		
		public ConfirmPaymentTransaction createTransaction(@Valid @RequestBody PaymentTransaction tran ){
			log.info("createTransaction:"+tran);
			return service.pay(tran);
		} 
}

