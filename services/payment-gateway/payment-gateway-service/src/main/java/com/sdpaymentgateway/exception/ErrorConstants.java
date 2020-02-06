package com.sdpaymentgateway.exception;

import java.util.ArrayList;
import java.util.List;

public class ErrorConstants {
	private List<ErrorConstant> errorCodes;

	public List<ErrorConstant> getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(List<ErrorConstant> errorCodes) {
		this.errorCodes = errorCodes;
	}
	
	public ErrorConstants(){}

	public ErrorConstants(List<ErrorConstant> errorCodes) {
		super();
		this.errorCodes = errorCodes;
	}

	public void add(ErrorConstant errorConstant) {
		if(errorCodes==null)
		{
			errorCodes = new ArrayList<ErrorConstant>();
		}			
		errorCodes.add(errorConstant);		
	}
	
	
	
}
