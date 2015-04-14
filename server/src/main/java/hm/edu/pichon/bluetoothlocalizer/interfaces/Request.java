package hm.edu.pichon.bluetoothlocalizer.interfaces;

import java.io.Serializable;


public abstract class Request implements Serializable {

	public final String requestCode;
	
	
	public Request() {
		requestCode = initRequestCode();
	}
	
	public boolean isGET() {
		return requestCode.equals("GET");
	}
	public boolean isPUT() {
		return requestCode.equals("PUT");
	}
	
	protected abstract String initRequestCode();
}
