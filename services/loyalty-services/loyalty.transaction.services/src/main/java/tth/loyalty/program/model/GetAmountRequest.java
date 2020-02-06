package tth.loyalty.program.model;

import java.io.Serializable;
 

public class GetAmountRequest  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long points; 
	
	public GetAmountRequest() { 
	}
	
	
	@Override
	public String toString() {
		return "GetAmountRequest [points=" + points + "]";
	}


	public GetAmountRequest(Long points) {
		super();
		this.points = points; 
	}


	public Long getPoints() {
		return points;
	}


	public void setPoints(Long points) {
		this.points = points;
	}
 
}
