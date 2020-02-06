package tth.loyalty.program.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tth.loyalty.program.model.LoyaltyTransaction;

@Repository
public interface LoyaltyTransactionRepo extends JpaRepository<LoyaltyTransaction, Long> {
	
	
	@Query("SELECT  LT.id, LT.type, LT.transactionDate, LT.transactionAmount, LT.loyaltyPoints,"
			+ "LT.transactionRemarks, LT.status, LT.source "
			+ "FROM LoyaltyTransaction LT  WHERE LT.loyaltyCustomer = loyaltyCustomer") 
	public List<LoyaltyTransaction> findAllByCustomerId(@Param ("loyaltyCustomer") Long loyaltyCustomer);

}
