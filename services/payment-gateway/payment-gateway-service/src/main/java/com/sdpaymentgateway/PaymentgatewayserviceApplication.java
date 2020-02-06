package com.sdpaymentgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.sdpaymentgateway.exception.ErrorConstants;
import com.sdpaymentgateway.service.MasterDataService;
import com.sdpaymentgateway.service.MasterDataServiceImpl;
import com.sdpaymentgateway.service.PaymentGatewayService;
import com.sdpaymentgateway.service.PaymentGatewayServiceImpl;

import brave.sampler.Sampler;


@EnableEurekaClient 
@SpringBootApplication
@ComponentScan(basePackages = {"com.sdpaymentgateway.api","com.sdpaymentgateway.data",
		 "com.sdpaymentgateway.exception","com.sdpaymentgateway.model",
		 "com.sdpaymentgateway.service", "com.sdpaymentgateway"
		 })
public class PaymentgatewayserviceApplication {
	
	 private ErrorConstants errorCodes;
	
	/**
	 * Bean declaration for Payment Gateway Service
	 * @return PaymentGatewayService
	 * 
	 */
	@Bean
	PaymentGatewayService getPaymentGatewayService(){
		return new PaymentGatewayServiceImpl();
	}
	
	/**
	 * Bean declaration for Master Data Service
	 * @return Master Data Service
	 * 
	 */
	@Bean
	MasterDataService getMasterDataService(){
		return new MasterDataServiceImpl();
	}
	
	public static void main(String[] args) { 		
		SpringApplication.run(PaymentgatewayserviceApplication.class, args);
	}

	public ErrorConstants getErrorCodes() {
		if(errorCodes == null)
		{
			MasterDataService masterDataService = getMasterDataService();
			errorCodes = masterDataService.LoadErrorCodes();
		}
		return errorCodes;
	} 

	@Bean
	public Sampler defaultSampler(){
		return Sampler.ALWAYS_SAMPLE;
	}
}
