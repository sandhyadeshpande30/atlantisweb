package tth.loyalty.program.api;

import java.util.Map;

import javax.persistence.NoResultException;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import tth.loyalty.program.exception.DefaultLoyaltyExceptionImpl;
import tth.loyalty.program.exception.LoyaltyException;

public class BaseController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @param exception
	 * @param httpServletReq
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(
			Exception exception, HttpServletRequest httpServletReq) {
		
		LoyaltyException exc = new DefaultLoyaltyExceptionImpl();
		Map<String, Object> excResponseMap = exc.getLoyaltyException(exception, httpServletReq, HttpStatus.INTERNAL_SERVER_ERROR);
				
		return new ResponseEntity<Map<String,Object>>(excResponseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	/**
	 * @param exception
	 * @param httpServletReq
	 * @return
	 */
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<Map<String, Object>> handleNoResultException(
			NoResultException exception, HttpServletRequest httpServletReq) {
		
		LoyaltyException exc = new DefaultLoyaltyExceptionImpl();
		Map<String, Object> excResponseMap = exc.getLoyaltyException(exception, httpServletReq, HttpStatus.NOT_FOUND);
				
		return new ResponseEntity<Map<String,Object>>(excResponseMap, HttpStatus.NOT_FOUND);
		
				
	}
	
	/**
	 * @param exception
	 * @param httpServletReq
	 * @return
	 */
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Map<String, Object>> handleAuthenticationException(
			AuthenticationException exception, HttpServletRequest httpServletReq) {
		
		LoyaltyException exc = new DefaultLoyaltyExceptionImpl();
		Map<String, Object> excResponseMap = exc.getLoyaltyException(exception, httpServletReq, HttpStatus.UNAUTHORIZED);
				
		return new ResponseEntity<Map<String,Object>>(excResponseMap, HttpStatus.UNAUTHORIZED);
		
				
	}
}
