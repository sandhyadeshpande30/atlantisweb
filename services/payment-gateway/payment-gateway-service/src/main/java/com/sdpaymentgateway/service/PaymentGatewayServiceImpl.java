package com.sdpaymentgateway.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sdpaymentgateway.data.TransactionRepository;
import com.sdpaymentgateway.exception.ExceptionDetail;
import com.sdpaymentgateway.exception.TransactionNotFoundException;
import com.sdpaymentgateway.model.ConfirmPaymentTransaction;
import com.sdpaymentgateway.model.PaymentTransaction;
import com.sdpaymentgateway.model.Query;

public class PaymentGatewayServiceImpl implements PaymentGatewayService {

	
	@Autowired
	TransactionRepository repo;
	
	private static final Logger log = LoggerFactory.getLogger(PaymentGatewayServiceImpl.class);

	@Override
	public Resource<PaymentTransaction> getTransactionDetails(Integer transactionId) {
		log.info("getTransactionDetails:" +transactionId );
		
		Optional<PaymentTransaction> tran = repo.findById(transactionId);
		
		if(!tran.isPresent())  				
			throw new TransactionNotFoundException("transactionId - " + transactionId);
		
		log.info("repo result:" + tran );
		Resource<PaymentTransaction> tr = new Resource<PaymentTransaction>(tran.get());
		
		//ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllTransactions());
		//tr.add(linkTo.withRel("all-Transactions"));
		log.info("repo result converted to Payment transaction :" + tr );
		
		return tr; 
	} 

	@Override
	@Valid
	public ConfirmPaymentTransaction pay(PaymentTransaction transactionDetails) {
		
		ConfirmPaymentTransaction confirmation = null;
		List<ExceptionDetail> exceptionDetails = null;
		ExceptionDetail exceptionDetail = null;
		PaymentTransaction transaction = null;
		
		try
		{
			if(transactionDetails != null){ 
				log.info("pay transaction details as given:" + transactionDetails );
				
				//set the transaction date to now		
				transactionDetails.setTransactionTime(new Date());
				
				log.info("pay transaction details by setting current date:" + transactionDetails.getTransactionTime() );
				
				log.info("save the transaction");
				//save the transaction
				transaction = repo.save(transactionDetails);
				
				log.info("get the id of the new saved transaction: " + transaction.getId());
				
				//get the id of the newly created transaction and include it in the location
				/*URI location =  ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{transactionId}")
				.buildAndExpand(transaction.getId()).toUri();*/
				
				//log.info("location=" + location.toString());
				
				//ResponseEntity<PaymentTransaction> tran = ResponseEntity.created(location).build();				
			}
			else{
				log.info("input not received");
				exceptionDetail = new ExceptionDetail("100", "Mandatory input missing", "UNABLE TO COMPLETE PAYMENT", new Date());
				exceptionDetails = new ArrayList<ExceptionDetail>();
				exceptionDetails.add(exceptionDetail);				
			}				
		}
		catch(Exception ex)
		{
			log.info("Exception occured =" + ex.toString());
			exceptionDetail = new ExceptionDetail("100", ex.getMessage(), "UNABLE TO COMPLETE PAYMENT", new Date());
			exceptionDetails = new ArrayList<ExceptionDetail>();
			exceptionDetails.add(exceptionDetail);
		} 

		log.info("set the confirmation response with transaction and exception detail if any and return");
		String port = Environment.getProperties().getProperty("local.server.port");
		confirmation = new ConfirmPaymentTransaction(transaction,exceptionDetails, port);  
		return confirmation;
	}
	
	@Override
	public List<PaymentTransaction> findTransactions(Query query) { 
		
		try{
			if(query == null){
				log.info("Query is null so findAll for now");
				return repo.findAll();
			}
			else{
				//TODO
			}				
		}
		catch(Exception ex)
		{
			log.info("Exception occured =" + ex.toString()); 
		} 

		return null;
	}


}
