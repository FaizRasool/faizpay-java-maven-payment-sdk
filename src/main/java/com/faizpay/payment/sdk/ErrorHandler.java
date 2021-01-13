package com.faizpay.payment.sdk;

/**
 * ErrorHandler class. Creates an Error Handler object.
 */

public class ErrorHandler {

	private String code, message;

	/**
	 * ErrorHandler constructor.
	 @param code error code
 	*/
	public ErrorHandler(String[] code) {
		this.code = code[0];
		this.message = code[1];
	}

	/**
	 * Code getter.
	 @return string
 	*/
	public String getCode() {
		return this.code;
	}

	/**
	 * Message getter.
	 @return string
 	*/
	public String getMessage() {
		return this.message;
	}

	/**
	 * Converts error object properties into a string.
	 * @return string
 	*/
	@Override
	public String toString() {
		return "ErrorHandler [code=" + code + ", message=" + message + "]";
	}

}