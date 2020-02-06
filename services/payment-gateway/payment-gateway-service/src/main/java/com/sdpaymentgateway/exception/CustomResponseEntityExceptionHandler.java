package com.sdpaymentgateway.exception;

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
 
	@SuppressWarnings("unchecked")
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ExceptionDetail exresponse = new ExceptionDetail(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage(), request.getDescription(true), new Date());
		return new ResponseEntity(exresponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(TransactionNotFoundException.class)
	public final ResponseEntity<Object> handleTransactionNotFoundException(Exception ex, WebRequest request) throws Exception {
		ExceptionDetail exresponse = new ExceptionDetail(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), request.getDescription(true), new Date());
		return new ResponseEntity(exresponse, HttpStatus.BAD_REQUEST);
	}
	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionDetail exresponse = new ExceptionDetail(HttpStatus.NOT_FOUND.toString(), ex.getMessage(), ex.getBindingResult().toString(), new Date());
		return new ResponseEntity(exresponse, HttpStatus.NOT_FOUND);
	}

	

}
