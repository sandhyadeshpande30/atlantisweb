package tth.loyalty.program.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import tth.loyalty.program.data.LoyaltyCustomerRepo;
import tth.loyalty.program.model.LoyaltyCustomer;

/**
 * @author M1006550
 * Implementation for Loyalty Customer Service
 *
 */
public class LoyaltyCustomerServiceImpl implements LoyaltyCustomerService {

	@Autowired
	LoyaltyCustomerRepo repository;
	
	/**
	 *Fetch customer by userID
	 */
	public LoyaltyCustomer getCustomerDetails(String userId) { 
		if(userId == null | userId.isEmpty()) {
			return null;
		}
		List<LoyaltyCustomer> customers = repository.find(userId);
		if(customers == null || customers.size() == 0) {
			return null;
		}
		return customers.get(0); 
	}

	public List<LoyaltyCustomer> findAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

}
