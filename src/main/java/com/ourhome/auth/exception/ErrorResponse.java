package com.ourhome.auth.exception;

public class ErrorResponse {
	private int httpStatus;
	private StringBuilder builder = new StringBuilder();
	
	public ErrorResponse(int errorCode) {
		this.httpStatus = errorCode;
	}
	
	public static ErrorResponse of(int errorCode) {
		return new ErrorResponse(errorCode);
	}
	
	public int getHttpStatus() {
		return httpStatus;
	}
	
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public String toString() {
		builder.append("{").append("\n")
		.append("\"httpStatus\": ").append(this.httpStatus).append("\n")
		.append("}");
		
		return builder.toString();
	}
	
}
