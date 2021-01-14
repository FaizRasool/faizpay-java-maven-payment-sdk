package com.faizpay.payment.sdk;

/**
 * User class. Creates a User object.
 */

public class User {

	/**
	 * user email
	 * user first name
	 * user last name
	 * user contact number
	 */
	private String email, firstName, lastName, contactNumber;

	/**
	 * Validates the inputs into the class
	 * @return ErrorHandler object or null
	*/
	public ErrorHandler isValid() {
		// validate email
		if (!this.email.equals("") && !this.isValidEmailAddress(email)) {
			return new ErrorHandler(Errors.CODE_11);
		}

		// validate greater than 255
		if (this.email.length() > 255) {
			return new ErrorHandler(Errors.CODE_15);
		}

		// validate greater than 255
		if (this.firstName.length() > 255) {
			return new ErrorHandler(Errors.CODE_12);
		}

		// validate greater than 255
		if (this.lastName.length() > 255) {
			return new ErrorHandler(Errors.CODE_13);
		}

		// validate greater than 255
		if (this.contactNumber.length() > 255) {
			return new ErrorHandler(Errors.CODE_14);
		}

		return null;
	}

	/**
	 * Validates the email input format
	 * @param email email input
	 * @return boolean 
	*/
	public boolean isValidEmailAddress(String email) {
		String pattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		if (email.matches(pattern)) {
			return true;
		}
		return false;
	}

	/**
	 * User constructor
	 * @param email user email
	 * @param firstName user first name
	 * @param lastName user last name
	 * @param contactNumber user contact number
	*/
	public User(String email, String firstName, String lastName, String contactNumber) {
		email = email.trim();
		firstName = firstName.trim();
		lastName = lastName.trim();
		contactNumber = contactNumber.trim();

		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
	}

	/**
	 * Email getter
	 * @return string 
	*/
	public String getEmail() {
		return this.email;
	}

	/**
	 * FirstName getter
	 * @return string 
	*/
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * LastName getter
	 * @return string 
	*/
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * ContactNumber getter
	 * @return string
	*/
	public String getContactNumber() {
		return this.contactNumber;
	}

}