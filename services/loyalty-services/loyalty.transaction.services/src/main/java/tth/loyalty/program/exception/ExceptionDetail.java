package tth.loyalty.program.exception;

import java.util.Date;

public class ExceptionDetail {

	private String code;
	private String details;
	private String message;
	private Date timestamp;
	
	public String getCode() {
		return code;
	}
	public String getDetails() {
		return details;
	}
	public String getMessage() {
		return message;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public ExceptionDetail(String code, String details, String message, Date timestamp) {
		super();
		this.code = code;
		this.details = details;
		this.message = message;
		this.timestamp = timestamp;
	} 
}
