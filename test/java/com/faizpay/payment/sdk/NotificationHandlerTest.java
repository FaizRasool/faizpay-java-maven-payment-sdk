package com.faizpay.payment.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.jupiter.api.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;

public class NotificationHandlerTest {

    private String terminalId = "1536bc14-9273-460e-9711-7e96733616fe";
    private String terminalSecret = "f9fd95d5-2acb-4643-b3fb-b16999f37175";

    private Connection getValidConnection() {
        return new Connection(this.terminalId, this.terminalSecret); 
    }

    @Test
    public void testErrorOnInvalidToken() {
        Connection connection = getValidConnection();
        NotificationHandler payment = new NotificationHandler(connection, "asd");
        assertThat(payment.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment.isValid()).getMessage(), Errors.CODE_16[1]);
    }

    @Test
    public void testErrorOnInvalidSecret() {

        Builder token = JWT.create()
				.withClaim("id", "1")
				.withClaim("orderID", "abc")
				.withClaim("requestAmount", "10")
				.withClaim("netAmount", "10")
                .withClaim("terminal", this.terminalId);
        
        String signedToken = token.sign(Algorithm.HMAC512("asd")); // asd is an invalid "terminalSecret"

        Connection connection = getValidConnection();

        NotificationHandler payment = new NotificationHandler(connection, signedToken);

        assertThat(payment.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment.isValid()).getMessage(), Errors.CODE_16[1]);
    }

    @Test
    public void testErrorOnExpireToken() {

        Builder token = JWT.create()
                .withClaim("exp", ((System.currentTimeMillis() / 1000) - 100))
				.withClaim("id", "1")
				.withClaim("orderID", "abc")
				.withClaim("requestAmount", "10")
				.withClaim("netAmount", "10")
                .withClaim("terminal", this.terminalId);
        
        String signedToken = token.sign(Algorithm.HMAC512(this.terminalSecret));

        Connection connection = getValidConnection();

        NotificationHandler payment = new NotificationHandler(connection, signedToken);

        assertThat(payment.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment.isValid()).getMessage(), Errors.CODE_16[1]);
    }

    @Test
    public void testErrorOnInvalidTokenContent() {

        Builder token = JWT.create()
				.withClaim("id", "1")
				.withClaim("netAmount", "10")
                .withClaim("terminal", this.terminalId);
        
        String signedToken = token.sign(Algorithm.HMAC512(this.terminalSecret));

        Connection connection = getValidConnection();

        NotificationHandler payment = new NotificationHandler(connection, signedToken);

        assertThat(payment.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment.isValid()).getMessage(), Errors.CODE_17[1]);
    }

    @Test
    public void testErrorOnTerminalMisMatch() {

        Builder token = JWT.create()
                .withClaim("id", "1")
                .withClaim("orderID", "abc")
                .withClaim("requestAmount", "10")
                .withClaim("netAmount", "10")
                .withClaim("terminal", this.terminalSecret);
        
        String signedToken = token.sign(Algorithm.HMAC512(this.terminalSecret));

        Connection connection = getValidConnection();

        NotificationHandler payment = new NotificationHandler(connection, signedToken);

        assertThat(payment.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment.isValid()).getMessage(), Errors.CODE_18[1]);
    }

    @Test
    public void testCreateTerminalOnValidData() {

        Builder token = JWT.create()
                .withClaim("id", "1")
                .withClaim("orderID", "abc")
                .withClaim("requestAmount", "10.00")
                .withClaim("netAmount", "10.00")
                .withClaim("terminal", this.terminalId);
        
        String signedToken = token.sign(Algorithm.HMAC512(this.terminalSecret));

        Connection connection = getValidConnection();

        NotificationHandler payment = new NotificationHandler(connection, signedToken);

        assertEquals(payment.isValid(), null);
        assertEquals(payment.getId(), "1");
        assertEquals(payment.getOrderID(), "abc");
        assertEquals(payment.getRequestedAmount(), "10.00");
        assertEquals(payment.getNetAmount(), "10.00");
        assertEquals(payment.getTerminal(), this.terminalId);
    }

    @Test
    public void testValidateAmountCheck() {

        Builder token = JWT.create()
                .withClaim("id", "1")
                .withClaim("orderID", "abc")
                .withClaim("requestAmount", "10.00")
                .withClaim("netAmount", "10.00")
                .withClaim("terminal", this.terminalId);
        
        String signedToken = token.sign(Algorithm.HMAC512(this.terminalSecret));

        Connection connection = getValidConnection();

        NotificationHandler payment = new NotificationHandler(connection, signedToken);

        assertEquals(payment.isValid(), null);
        assertTrue(payment.validateAmount("10.00"));
        assertTrue(payment.validateAmount("10"));
        assertTrue(payment.validateAmount("10.00000000000000"));
        assertFalse(payment.validateAmount("11.00000001"));
        assertFalse(payment.validateAmount("ABC"));
        assertFalse(payment.validateAmount("A$$!@£!@£!@3BC"));
    }
}
