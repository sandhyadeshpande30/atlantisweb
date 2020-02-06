package tth.loyalty.program.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

public interface LoyaltyException {

	public Map<String, Object> getLoyaltyException(
			Exception exception, HttpServletRequest httpServletReq, HttpStatus httpStatus);
}
