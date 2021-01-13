package com.faizpay.payment.sdk;

/**
 * Provider class. Creates a Provider object.
 */

public class Provider {

	/**
	 * Provider ID, sort code, account number 
	*/
	private String providerId, sortCode, accountNumber;

	/**
	 * Validates the inputs into the class
	 * @return ErrorHandler object or null
 	*/
	public ErrorHandler isValid() {

		if (!this.sortCode.equals("") && this.sortCode.length() != '6') {
			return new ErrorHandler(Errors.CODE_7);
		}

		if (!this.accountNumber.equals("") && this.accountNumber.length() != '8') {
			return new ErrorHandler(Errors.CODE_8);
		}

		if (this.providerId.equals("") && (!this.sortCode.equals("") || !this.accountNumber.equals(""))) {
			return new ErrorHandler(Errors.CODE_9);
		}

		if (!this.sortCode.equals("") && this.accountNumber.equals("")) {
			return new ErrorHandler(Errors.CODE_10);
		}

		if (!this.accountNumber.equals("") && this.sortCode.equals("")) {
			return new ErrorHandler(Errors.CODE_10);
		}
		return null;
	}

	/**
	 * Provider constructor 
	 * @param providerId provider ID
	 * @param sortCode sort code
	 * @param accountNumber account number 
 	*/
	public Provider(String providerId, String sortCode, String accountNumber) {
		this.providerId = providerId.trim();
		this.sortCode = sortCode.trim();
		this.accountNumber = accountNumber.trim();

		this.providerId = providerId;
		this.sortCode = sortCode;
		this.accountNumber = accountNumber;
	}

	/**
	 * providerId getter
	 * @return string
 	*/
	public String getProviderId() {
		return this.providerId;
	}

	/**
	 * sortCode getter
	 * @return string
 	*/
	public String getSortCode() {
		return this.sortCode;
	}

	/**
	 * accountNumber getter
	 * @return string
 	*/
	public String getAccountNumber() {
		return this.accountNumber;
	}
}
