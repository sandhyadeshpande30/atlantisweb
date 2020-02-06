package tth.loyalty.program.exception;

public class InvalidRequestException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public InvalidRequestException(String errorMsg){
		this.errorMsg = errorMsg;
	}
	public InvalidRequestException() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getLocalizedMessage() {
		// TODO Auto-generated method stub
		return errorMsg;
	}
	

}
