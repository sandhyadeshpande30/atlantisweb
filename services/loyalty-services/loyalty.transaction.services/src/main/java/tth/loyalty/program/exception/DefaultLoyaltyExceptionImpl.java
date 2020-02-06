package tth.loyalty.program.exception;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

/**
 * @author M1006550
 *
 */
public class DefaultLoyaltyExceptionImpl implements LoyaltyException {

	public static final String TIMESTAMP = "timestamp";
	public static final String STATUS = "status";
	public static final String ERROR = "error";
	public static final String EXCEPTION = "exception";
	public static final String MESSAGE = "message";
	public static final String PATH = "path";
	
	
	/**
	 *
	 */
	public Map<String, Object> getLoyaltyException(Exception exception, HttpServletRequest httpServletReq,
			HttpStatus httpStatus) {

		Map<String, Object> exceptionAttr = new LinkedHashMap<String, Object>();
		
		addStatus(exceptionAttr, httpStatus);
		addExceptionDetails(exceptionAttr, exception);
		addPath(exceptionAttr, httpServletReq);
		addTimeStamp(exceptionAttr);
		
		return exceptionAttr;
	}

	/**
	 * @param exceptionAttr
	 */
	private void addTimeStamp(Map<String, Object> exceptionAttr) {
		
		exceptionAttr.put(TIMESTAMP, Calendar.getInstance().getTime().toString());
	}

	/**
	 * @param exceptionAttr
	 * @param httpStatus
	 */
	private void addPath(Map<String, Object> exceptionAttr, HttpServletRequest httpServletReq) {
		
		exceptionAttr.put(PATH, httpServletReq.getServletPath());
	}

	/**
	 * @param exceptionAttr
	 * @param exception2
	 */
	private void addExceptionDetails(Map<String, Object> exceptionAttr, Exception exception) {
		
		exceptionAttr.put(EXCEPTION, exception.getClass().getName());
		exceptionAttr.put(MESSAGE, exception.getMessage());
	}

	/**
	 * @param exceptionAttr
	 * @param httpStatus
	 */
	private void addStatus(Map<String, Object> exceptionAttr, HttpStatus httpStatus) {
		
		exceptionAttr.put(STATUS, httpStatus.value());
		exceptionAttr.put(ERROR, httpStatus.getReasonPhrase());
	}

}
