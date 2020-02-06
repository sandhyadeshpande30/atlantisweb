package com.sdpaymentgateway.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sdpaymentgateway.data.TransactionRepository;
import com.sdpaymentgateway.model.ConfirmPaymentTransaction;
import com.sdpaymentgateway.model.PaymentTransaction;
import com.sdpaymentgateway.service.PaymentGatewayServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class PaymentGatewayServiceTest {

	@Mock
	private TransactionRepository transactionRepoMock;

	@InjectMocks
	private PaymentGatewayServiceImpl paymentServiceImpl;

	private static PaymentTransaction transaction;
	private static PaymentTransaction savedTransaction;
	private static ConfirmPaymentTransaction confirmedTransaction;
	private static List<PaymentTransaction> transactions;
	private static List<PaymentTransaction> retrievedTransactions;

	@BeforeClass
	public static void setupForAllTests() {
		savedTransaction = new PaymentTransaction(1, new Date(), "spd from", "test account", new BigDecimal(200),
				"transfer");
		transactions = new ArrayList<PaymentTransaction>();
		transaction = new PaymentTransaction(1, new Date(), "spd from", "test account", new BigDecimal(200),
				"transfer");
		transactions.add(transaction);
		transaction = new PaymentTransaction(2, new Date(), "spd from", "test account", new BigDecimal(200),
				"transfer");
		transactions.add(transaction);
	}

	@AfterClass
	public static void cleanupForAllTests() {
		transactions = null;
		transaction = null;
		retrievedTransactions = null;
		confirmedTransaction = null;
	}

	@After
	public void cleanupForTheTest() {
		transaction = null;
	}

	// get all transactions
	@Test
	public void testFindAllTransaction() {
		when(transactionRepoMock.findAll()).thenReturn(transactions);
		retrievedTransactions = paymentServiceImpl.findTransactions(null);
		assertEquals(2, retrievedTransactions.size());
	}

	// Pay
	// Get specific transaction

	// Successful payment transaction creation where id and date are set
	@Test
	public void createTransactionSuccess() throws Exception {
		BuildPaymentTransactionSuccess();
		when(transactionRepoMock.save(transaction)).thenReturn(savedTransaction);
		confirmedTransaction = paymentServiceImpl.pay(transaction);
		assertNotNull(confirmedTransaction);
		PaymentTransaction tr = confirmedTransaction.getPaymentTransaction();
		assertNotNull(tr);
		assertNotNull(tr.getId());
		assertNotNull(tr.getTransactionTime());
	}
	
	
	// Invalid request due to mandatory data missing throws exception
	@Test //(expected = Exception.class)
	public void createTransactionInvalidRequestMandatoryDataMissing() throws Exception {
		BuildPaymentTransactionMandatoryDataMissing();
		when(transactionRepoMock.save(transaction)).thenReturn(transaction);
		confirmedTransaction = paymentServiceImpl.pay(transaction);
	}

	/*
	// Incorrect data so failed to complete payment
	// This cannot be tested as in real time so bad format is used
	@Test //(expected = Exception.class)
	public void createTransactionInvalidRequestIncorrectData() {
		BuildPaymentTransactionIncorrectData();
		when(transactionRepoMock.save(transaction)).thenReturn(savedTransaction);
		confirmedTransaction = paymentServiceImpl.pay(transaction);
	}
*/
	private void BuildPaymentTransactionSuccess() {
		// Create valid data
		transaction = new PaymentTransaction();
		transaction.setAmount(BigDecimal.ONE);
		transaction.setTo("someone");
		transaction.setFrom("spd");
	}

	private void BuildPaymentTransactionMandatoryDataMissing() {
		// Create empty transaction so all mandatory data is missing
		transaction = new PaymentTransaction();
	}

	private void BuildPaymentTransactionIncorrectData() {
		// Create transaction with invalid data for mandatory elements
		transaction = new PaymentTransaction();
		transaction.setAmount(BigDecimal.valueOf(-1)); // only positive
														// supported
		transaction.setTo("s"); // min 2 char supported
		transaction.setFrom("d"); // min 2 char supported
	}

}
