package com.sdpaymentgateway.tests;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdpaymentgateway.api.PaymentTransactionController;
import com.sdpaymentgateway.data.TransactionRepository;
import com.sdpaymentgateway.model.ConfirmPaymentTransaction;
import com.sdpaymentgateway.model.PaymentTransaction;

//@RunWith(MockitoJUnitRunner.class)

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentTransactionController.class)
public class PaymentTransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TransactionRepository transactionRepoMock;


	@MockBean
	private PaymentTransactionController controller;

	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testCreateTransactionSuccess() throws Exception {
		PaymentTransaction tran = new 
				PaymentTransaction(1, new Date(), 
						"spd from", "test account", 
						new BigDecimal(200),
						"transfer remarks ");

		ConfirmPaymentTransaction confirmation = new ConfirmPaymentTransaction(tran, null,null);

		given(controller.createTransaction(any(PaymentTransaction.class))).willReturn(confirmation);

		String res = mockMvc
				.perform(post("/transactions").content(mapper.writeValueAsString(tran)).contentType(APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		ConfirmPaymentTransaction savedTran = mapper.readValue(res, ConfirmPaymentTransaction.class);
		assertNotNull(savedTran);
		PaymentTransaction savedTranDetail = savedTran.getPaymentTransaction();
		assertNotNull(savedTranDetail);
		assertNotNull(savedTranDetail.getId());
		assertNotNull(savedTranDetail.getTransactionTime());
	}
	

    @Test
    public void testCreateTransactionFailDueToMissingMandatoryInfo() throws Exception {
        PaymentTransaction tran = new PaymentTransaction();

        mockMvc.perform(post("/transactions")
            .content(mapper.writeValueAsString(tran))
            .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void testCreateTransactionFailDueToEmptyPayload() throws Exception {
        PaymentTransaction tran = null;

        mockMvc.perform(post("/transactions")
            .content(mapper.writeValueAsString(tran))
            .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateTransactionFailDueToMissingAmount() throws Exception {
    	PaymentTransaction tran = new PaymentTransaction();
    	tran.setFrom("from");
    	tran.setTo("to");

        mockMvc.perform(post("/transactions")
            .content(mapper.writeValueAsString(tran))
            .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void testCreateTransactionFailDueToMissingFrom() throws Exception {
    	PaymentTransaction tran = new PaymentTransaction();
    	tran.setAmount(new BigDecimal(20)); 
    	tran.setTo("to");

        mockMvc.perform(post("/transactions")
            .content(mapper.writeValueAsString(tran))
            .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void testCreateTransactionFailDueToMissingTo() throws Exception {
    	PaymentTransaction tran = new PaymentTransaction();
    	tran.setAmount(new BigDecimal(20)); 
    	tran.setFrom("from");

        mockMvc.perform(post("/transactions")
            .content(mapper.writeValueAsString(tran))
            .contentType(APPLICATION_JSON)
        )
            .andExpect(status().isNotFound());
    } 
     
}