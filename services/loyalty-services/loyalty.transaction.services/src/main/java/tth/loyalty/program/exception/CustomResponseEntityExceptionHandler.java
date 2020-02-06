package tth.loyalty.program.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
  
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ExceptionDetail exresponse = new ExceptionDetail(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage(), request.getDescription(true), new Date());
		return new ResponseEntity<Object>(exresponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidRequestException.class)
	public final ResponseEntity<Object> handleInvalidRequestException(Exception ex, WebRequest request) throws Exception {
		ExceptionDetail exresponse = new ExceptionDetail(HttpStatus.BAD_REQUEST.toString(), 
					ex.getMessage() + " " + ex.getLocalizedMessage()
				, request.getDescription(true), new Date());
		return new ResponseEntity<Object>(exresponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnableToCompletePaymentException.class)
	public final ResponseEntity<Object> handleUnableToCompletePaymentException(Exception ex, WebRequest request) throws Exception {
		ExceptionDetail exresponse = new ExceptionDetail(HttpStatus.BAD_GATEWAY.toString(), ex.getMessage(), request.getDescription(true), new Date());
		return new ResponseEntity<Object>(exresponse, HttpStatus.BAD_GATEWAY);
	}	
		
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionDetail exresponse = new ExceptionDetail(HttpStatus.NOT_FOUND.toString(), ex.getMessage(), ex.getBindingResult().toString(), new Date());
		return new ResponseEntity<Object>(exresponse, HttpStatus.NOT_FOUND);
	}

	

}
