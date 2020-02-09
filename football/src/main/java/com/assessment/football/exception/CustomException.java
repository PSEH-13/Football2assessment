package com.assessment.football.exception;

public class CustomException extends Exception{

	private static final long serialVersionUID = 1L;
	private String statusCode;
	private String statusMsg;
	
	public CustomException(String statusCode, String statusMsg) {
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
