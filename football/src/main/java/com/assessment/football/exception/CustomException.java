package com.assessment.football.exception;

public class VaildationException extends Exception{

	private static final long serialVersionUID = 1L;
	private String statusCode;
	private String statusMsg;
	
	public VaildationException(String statusCode, String statusMsg) {
		super();
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getStatusMsg() {
		return statusMsg;
	}
	
}
