package com.faizpay.payment.sdk;

/**
 * NumberFormatter class. Used to format and validate numbers..
 */

public class NumberFormatter {

	/**
	 * @param number number input
	 * @return string
	 */
	public static String formatNumber(String number) {
		return String.format("%.2f", Double.parseDouble(number));
	}

	/**
	 * @param number number input
	 * @return boolean
	 */
	public static boolean validateTwoDecimals(String number) {
		if (number.matches("^[0-9]*\\.[0-9]{2}$")) {
			return true;
		}
		return false;
	}
}
