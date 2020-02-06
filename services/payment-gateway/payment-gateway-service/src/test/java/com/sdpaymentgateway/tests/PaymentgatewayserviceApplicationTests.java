package com.sdpaymentgateway.tests;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sdpaymentgateway.api.PaymentTransactionController;
import com.sdpaymentgateway.model.PaymentTransaction;

@SpringBootTest
class PaymentgatewayserviceApplicationTests {
	
	PaymentTransaction tran;
	
	@Autowired
	PaymentTransactionController controller;

	@Test
	void contextLoads() {
	}
	
	//Successful payment transaction creation
	@Test
	void createTransactionSuccess() {
		BuildPaymentTransactionSuccess(); 
		controller.createTransaction(tran);
		//assert id is not null
		//assert date is not null
		cleanUp();
	}

	//Invalid request due to wrong param data
	@Test()
	void createTransactionInvalidRequestMandatoryDataMissing() {
		BuildPaymentTransactionMandatoryDataMissing(); 
		controller.createTransaction(tran);
		//assert exception 
		cleanUp();
	}

	//Incorrect data so failed to complete payment 
	@Test()
	void createTransactionInvalidRequestIncorrectData() {
		BuildPaymentTransactionIncorrectData(); 
		controller.createTransaction(tran);
		//assert exception 
		cleanUp();
	}

	//Connection not available and hence failed

	//Invalid request due to wrong param data

	private void BuildPaymentTransactionSuccess() {
		// Create valid data
		tran = new PaymentTransaction();
		tran.setAmount(BigDecimal.ONE);
		tran.setTo("someone");
		tran.setFrom("spd"); 
	}

	private void cleanUp() {
		tran = null;
	}
	
	private void BuildPaymentTransactionMandatoryDataMissing() {
		tran = new PaymentTransaction();
	}

	private void BuildPaymentTransactionIncorrectData() {
		tran = new PaymentTransaction();
		tran.setAmount(BigDecimal.valueOf(-1));
		tran.setTo("s");
		tran.setFrom("d"); 
	}
	
	
	
}
