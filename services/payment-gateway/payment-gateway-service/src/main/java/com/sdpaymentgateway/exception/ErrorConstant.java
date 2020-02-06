package com.sdpaymentgateway.exception;

public class ErrorConstant {
	
	private String code;
	private String message;
	
	public ErrorConstant(){}

	public ErrorConstant(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
