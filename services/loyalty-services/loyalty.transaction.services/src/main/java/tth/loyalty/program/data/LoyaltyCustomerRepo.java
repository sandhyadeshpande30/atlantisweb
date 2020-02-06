package tth.loyalty.program.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import tth.loyalty.program.model.LoyaltyCustomer;

@Repository
public interface LoyaltyCustomerRepo extends JpaRepository<LoyaltyCustomer, Long> {

	@Query("SELECT lc FROM LoyaltyCustomer lc WHERE LOWER(lc.userID) = LOWER(:userID)")
	public List<LoyaltyCustomer> find(@Param ("userID") String userID);
}


