package com.faizpay.payment.sdk;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


/**
 * NotificationHandler class. Creates a Notification object.
 */

public class NotificationHandler {

	/**
	 * Connection object 
	*/
	private Connection connection;
	/**
	 * Token JSON object 
	*/
	private JSONObject token;
	
	/**
	 * Validates the token passed into the class.
	 * @return ErrorHandler object or null
 	*/
	public ErrorHandler isValid() {
		if (this.token == null) {
			return new ErrorHandler(Errors.CODE_16);
		}

		if (this.token.isEmpty() || this.token.get("id") == null || this.token.get("orderID") == null
				|| this.token.get("requestAmount") == null || this.token.get("netAmount") == null
				|| this.token.get("terminal") == null) {
			return new ErrorHandler(Errors.CODE_17);
		}

		if (!this.token.get("terminal").equals(connection.getTerminalId())) {
			return new ErrorHandler(Errors.CODE_18);
		}

		return null;
	}

	/**
	 * NotificationHandler constructor.
	 * 
	 * @param connection connection object input
	 * @param token token input
	 */
	public NotificationHandler(Connection connection, String token) {
		this.connection = connection;
		try {
			JSONParser parser = new JSONParser();
			JWTVerifier verifier = JWT.require(Algorithm.HMAC512(connection.terminalSecret)).build();
			DecodedJWT decodedToken = verifier.verify(token);
			String payload = decodedToken.getPayload();
			String decodedString = new String(Base64.decodeBase64(payload.getBytes()));
			this.token = (JSONObject) parser.parse(decodedString);
		} catch (Exception e) {

		}
	}

	/**
	 * validates if requested amount matches the payment
	 * 
	 * @param requestedAmount string original amount requested for user to pay
	 * @return boolean
	 */
	public boolean validateAmount(String requestedAmount) {
		// validate amount
		if (!requestedAmount.matches("-?\\d+(\\.\\d+)?")) {
			return false;
		}

		// verify the amount
		if (!NumberFormatter.formatNumber(this.token.get("requestAmount").toString())
				.equals(NumberFormatter.formatNumber(requestedAmount))) {
			return false;
		}

		return true;
	}

	/**
	 * return the order id sent in the payment should be used to read the payment
	 * record client system
	 * 
	 * @return string
	 */
	public String getOrderID() {
		return this.token.get("orderID").toString();
	}

	/**
	 * returns the amount requested
	 * 
	 * @return string
	 */
	public String getRequestedAmount() {
		return this.token.get("requestAmount").toString();
	}

	/**
	 * returns the amount user actually paid
	 * 
	 * @return string
	 */

	public String getNetAmount() {
		return this.token.get("netAmount").toString();
	}

	/**
	 * returns the FaizPay payment ID
	 * 
	 * @return string
	 */
	public String getId() {
		return this.token.get("id").toString();
	}

	/**
	 * returns the payment terminal ID
	 * 
	 * @return string
	 */
	public String getTerminal() {
		return this.token.get("terminal").toString();
	}

}
