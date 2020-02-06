package tth.loyalty.program;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import brave.sampler.Sampler;
import tth.loyalty.program.service.LoyaltyCustomerService;
import tth.loyalty.program.service.LoyaltyCustomerServiceImpl;
import tth.loyalty.program.service.LoyaltyTransactionService;
import tth.loyalty.program.service.LoyaltyTransactionServiceImpl;


/**
 * @author M1006550
 * Loyalty Transaction Service main Application
 * 
 */

 @EnableHystrix
 @SpringBootApplication
 @EnableEurekaClient 
 @EnableFeignClients({"tth.loyalty.program.api","tth.loyalty.program.service",
		 "tth.loyalty.program.data","tth.loyalty.program.model",
		 "tth.loyalty.program.exception","tth.loyalty.program"
		 })
 @ComponentScan(basePackages = {"tth.loyalty.program.api","tth.loyalty.program.service",
		 "tth.loyalty.program.data","tth.loyalty.program.model",
		 "tth.loyalty.program.exception","tth.loyalty.program"
		 })
public class Application {
	
	
	  
	@Bean
	LoyaltyCustomerService getLoyaltyCustomerService(){
		return new LoyaltyCustomerServiceImpl();
	}
	
	@Bean
	LoyaltyTransactionService getLoyaltyTransactionService(){
		return new LoyaltyTransactionServiceImpl();
	}
		
    /**
     * Spring boot Application runner
     * @param args
     * @throws Exception
     */
    public static void main( String[] args ) throws Exception
    {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
	public Sampler defaultSampler(){
		return Sampler.ALWAYS_SAMPLE;
	}
}
