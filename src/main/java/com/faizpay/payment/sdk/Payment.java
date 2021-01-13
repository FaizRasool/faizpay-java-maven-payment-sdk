package com.faizpay.payment.sdk;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * Payment class. Creates a Payment object.
 */

public class Payment {

	/**
	 * Expiration time for the token 
	*/
	private final int tokenExpiry = 3600 * 2; // 2 Hours
	/**
	 * Redirect url 
	*/
	private final String endpoint = "https://app.faizpay.com/pay?token=";

	/**
	 * Connection object 
	*/
	private Connection connection;
	/**
	 * User object 
	*/
	private User user;
	/**
	 * Provider object 
	*/
	private Provider provider;
	/**
	 * Order ID 
	*/
	private String orderId;
	/**
	 * Amount 
	*/
	private String amount;

	/**
	 * Validates the inputs into the class.
	 @return ErrorHandler object or null
 	*/
	public ErrorHandler isValid() {
		// validate order Id
		if (this.orderId.equals("")) {
			return new ErrorHandler(Errors.CODE_3);
		}

		// validate amount
		if (!NumberFormatter.validateTwoDecimals(this.amount)) {
			return new ErrorHandler(Errors.CODE_5);
		}

		// validate amount
		if (this.amount.equals("") || this.amount.equals("0.00") || Double.parseDouble(this.amount) < 0) {
			return new ErrorHandler(Errors.CODE_4);
		}

		// validate order is greater than 255
		if (this.orderId.length() > 255) {
			return new ErrorHandler(Errors.CODE_6);
		}
		return null;
	}

	/**
	 * Payment constructor
	 * @param connection Connection object
	 * @param orderId Order ID
	 * @param amount Amount
 	*/
	public Payment(Connection connection, String orderId, String amount) {
		orderId = orderId.trim();

		this.connection = connection;
		this.orderId = orderId;
		this.amount = amount;
	}

	/**
	 * User setter
	 * @param user User object
	 * @return User
 	*/
	public User setUser(User user) {
		this.user = user;
		return this.user;
	}

	/**
	 * Provider setter
	 * @param provider Provider object
	 * @return Provider
 	*/
	public Provider setProvider(Provider provider) {
		this.provider = provider;
		return this.provider;
	}

	/**
	 * Processes the payment
	 * @return string
 	*/
	public String process() {
		Builder jwtBuilder = JWT.create() //
				.withClaim("iat", System.currentTimeMillis() / 1000) //
				.withClaim("exp", (System.currentTimeMillis() / 1000) + this.tokenExpiry) //
				.withClaim("terminalID", this.connection.getTerminalId()) //
				.withClaim("orderID", this.orderId) //
				.withClaim("amount", amount) //
				.withClaim("email", "") //
				.withClaim("firstName", "") //
				.withClaim("lastName", "") //
				.withClaim("contactNumber", "") //
				.withClaim("bankID", "") //
				.withClaim("sortCode", "") //
				.withClaim("accountNumber", "");

		if (this.user instanceof User) {
			jwtBuilder.withClaim("email", this.user.getEmail()) //
					.withClaim("firstName", this.user.getFirstName()) //
					.withClaim("lastName", this.user.getLastName()) //
					.withClaim("contactNumber", this.user.getContactNumber());
		}

		if (this.provider instanceof Provider) {
			jwtBuilder.withClaim("bankID", this.provider.getProviderId()) //
					.withClaim("sortCode", this.provider.getSortCode()) //
					.withClaim("accountNumber", this.provider.getAccountNumber());
		}
		return this.endpoint + jwtBuilder.sign(Algorithm.HMAC512(this.connection.getTerminalSecret()));
	}

}
