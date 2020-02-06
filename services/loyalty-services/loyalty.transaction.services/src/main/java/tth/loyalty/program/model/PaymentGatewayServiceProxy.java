package tth.loyalty.program.model;

import javax.validation.Valid;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
 

//@FeignClient(name="payment-gateway-service", url="localhost:8080")

//@FeignClient(name="payment-gateway-service")
//@FeignClient(name="loyalty.discovery.service")
@FeignClient(name="zuul-api-gateway-server")
@RibbonClient(name="payment-gateway-service")
public interface PaymentGatewayServiceProxy {
	
	//@PostMapping(path="/transactions")
	@PostMapping(path="/payment-gateway-service/transactions")
	@Valid
	public ConfirmPaymentTransaction createTransaction(@Valid @RequestBody PaymentTransaction tran);
	

}
