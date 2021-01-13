package com.faizpay.payment.sdk;

/**
 * Errors class. Contains the errors list of the SDK.
 */

public class Errors {

    /**
	 * Error code 1 
	*/
	public final static String[] CODE_1 = {"1", "Invalid Terminal ID - Should be valid UUID4"};
    /**
	 * Error code 2 
	*/
    public final static String[] CODE_2 = {"2", "Invalid Terminal Secret - Should be valid UUID4"};
    /**
	 * Error code 3 
	*/
    public final static String[] CODE_3 = {"3", "Order ID cannot be empty"};
    /**
	 * Error code 4 
	*/
    public final static String[] CODE_4 = {"4", "Amount cannot be empty or less than 0.01"};
    /**
	 * Error code 5 
	*/
    public final static String[] CODE_5 = {"5", "Number must be 2 decimal places"};
    /**
	 * Error code 6 
	*/
    public final static String[] CODE_6 = {"6", "Order ID cannot be greater than 255 characters"};
    /**
	 * Error code 7 
	*/
    public final static String[] CODE_7 = {"7", "Invalid Sort Code"};
    /**
	 * Error code 8 
	*/
    public final static String[] CODE_8 = {"8", "Invalid Account Number"};
    /**
	 * Error code 9 
	*/
    public final static String[] CODE_9 = {"9", "Provider ID must be set to set sort code and account number"};
    /**
	 * Error code 10 
	*/
    public final static String[] CODE_10 = {"10", "Sort Code and account both must be set"};
    /**
	 * Error code 11 
	*/
    public final static String[] CODE_11 = {"11", "Invalid email is given"};
    /**
	 * Error code 12 
	*/
    public final static String[] CODE_12 = {"12", "First name need to be less than 255"};
    /**
	 * Error code 13 
	*/
    public final static String[] CODE_13 = {"13", "Last name need to be less than 255"};
    /**
	 * Error code 14 
	*/
    public final static String[] CODE_14 = {"14", "Contact Number need to be less than 255"};
    /**
	 * Error code 15 
	*/
    public final static String[] CODE_15 = {"15", "Email need to be less than 255"};
    /**
	 * Error code 16 
	*/
    public final static String[] CODE_16 = {"16", "Unable to decode the token - Invalid / Expire token given"};
    /**
	 * Error code 17 
	*/
    public final static String[] CODE_17 = {"17", "Token content missing data"};
    /**
	 * Error code 18 
	*/
    public final static String[] CODE_18 = {"18", "Token terminal mismatch"};
	
}