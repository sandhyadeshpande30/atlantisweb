package tth.loyalty.program.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tth.loyalty.program.model.LoyaltyCustomer;

/**
 * @author M1006550
 * Service interface for Loyalty Customer
 */
@Service("LoyaltyCustomerService")
public interface LoyaltyCustomerService {

	/**
	 * Get details for requested Customer
	 * @param customer
	 * @return
	 */
	LoyaltyCustomer getCustomerDetails(String user);
	
	/**
	 * Get details for all Customers
	 * @return
	 */
	List<LoyaltyCustomer> findAllCustomers();
}
