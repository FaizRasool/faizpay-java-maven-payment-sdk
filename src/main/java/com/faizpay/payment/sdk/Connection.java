package com.faizpay.payment.sdk;

/**
 * Connection class. Creates a Connection object.
 */

public class Connection {

	/**
	 * Terminal ID
	*/
	public String terminalId;
	/**
	 * Terminal Secret 
	*/
	public String terminalSecret;

	/**
	 * Validates the terminal ID and terminal Secret.
	 @return ErrorHandler object or null
 	*/
	public ErrorHandler isValid() {
		if (!validate(this.terminalId)) {
			return new ErrorHandler(Errors.CODE_1);
		}

		if (!validate(this.terminalSecret)) {
			return new ErrorHandler(Errors.CODE_2);
		}
		
		return null;
	}

	/**
	 * Validates the UUID string format.
	 @param uuid input in the UUID format
	 @return boolean
 	*/
	private boolean validate(String uuid) {
		if (uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
			return true;
		}
		return false;
	}

	/**
	 * Connection constructor.
	 @param terminalId Terminal ID
	 @param terminalSecret Terminal Secret
 	*/
	public Connection(String terminalId, String terminalSecret) {
		this.terminalId = terminalId;
		this.terminalSecret = terminalSecret;
	}

	/**
	 * Terminal Secret getter.
	 @return string
 	*/
	public String getTerminalSecret() {
		return this.terminalSecret;
	}

	/**
	 * Terminal ID getter.
	 @return string
 	*/
	public String getTerminalId() {
		return this.terminalId;
	}
}