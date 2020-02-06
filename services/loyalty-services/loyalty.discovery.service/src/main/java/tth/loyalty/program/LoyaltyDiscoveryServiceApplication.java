package tth.loyalty.program;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author M1006550
 * 
 * Loyalty Discovery Service main class
 *
 */

@SpringBootApplication
@EnableEurekaServer
public class LoyaltyDiscoveryServiceApplication {
	
	/**
	 * Application runner
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(LoyaltyDiscoveryServiceApplication.class, args);
	}
}
