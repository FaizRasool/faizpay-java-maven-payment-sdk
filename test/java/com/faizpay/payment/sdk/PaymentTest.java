package com.faizpay.payment.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import org.json.simple.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;

public class PaymentTest {

    private String terminalId = "1536bc14-9273-460e-9711-7e96733616fe";
    private String terminalSecret = "f9fd95d5-2acb-4643-b3fb-b16999f37175";

    private Connection getValidConnection() {
        return new Connection(this.terminalId, this.terminalSecret);
    }

    private JSONObject  getToken(String order, String amount, User user, Provider provider) {
        JSONParser parser = new JSONParser();
        Connection connection = getValidConnection();
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(connection.terminalSecret)).build();
        Payment payment = new Payment(connection, order, amount);
        payment.setProvider(provider);
        payment.setUser(user);
        String url = payment.process();
        String token = (url.split("="))[1];

        DecodedJWT decodedToken = verifier.verify(token);
        String payload = decodedToken.getPayload();
        String decodedString = new String(Base64.decodeBase64(payload.getBytes()));

        try {
            JSONObject finalToken =  (JSONObject) parser.parse(decodedString);
            return finalToken; 
        } catch (Exception e){
            return  null;
        }
    }

    private JSONObject  getToken(String order, String amount) {
        JSONParser parser = new JSONParser();
        Connection connection = getValidConnection();
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(connection.terminalSecret)).build();
        Payment payment = new Payment(connection, order, amount);
        String url = payment.process();
        String token = (url.split("="))[1];
        DecodedJWT decodedToken = verifier.verify(token);
        String payload = decodedToken.getPayload();
        String decodedString = new String(Base64.decodeBase64(payload.getBytes()));
        // Error below: can't parse the "decodedString"
        try {
            JSONObject finalToken =  (JSONObject) parser.parse(decodedString);
            return finalToken; 
        } catch (Exception e){
            return  null;
            }
    }

    @Test
    public void testErrorOnEmptyOrderId() {
        Connection connection = getValidConnection();
        Payment payment = new Payment(connection, "", "10.00");
        assertThat(payment.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment.isValid()).getMessage(), Errors.CODE_3[1]);
    }

    @Test
    public void testErrorOnSpaceTabOrderId() {
        Connection connection = getValidConnection();

        Payment payment1 = new Payment(connection, "  ", "10.00");
        assertThat(payment1.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment1.isValid().getMessage()), Errors.CODE_3[1]);

        Payment payment2 = new Payment(connection, "         ", "10.00");
        assertThat(payment2.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment2.isValid().getMessage()), Errors.CODE_3[1]);
    }

    @Test
    public void testErrorOnOrderIdGreaterThan255() {
        Connection connection = getValidConnection();
        
        Payment payment = new Payment(connection, "a".repeat(256), "10.00");
        assertThat(payment.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment.isValid()).getMessage(), Errors.CODE_6[1]);
    }

    @Test
    public void testErrorOnZeroAmount() {
        Connection connection = getValidConnection();
        
        Payment payment = new Payment(connection, "abc", "0.00");
        assertThat(payment.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment.isValid()).getMessage(), Errors.CODE_4[1]);
    }

    @Test
    public void testErrorOnEmptyAmount() {
        Connection connection = getValidConnection();
        
        Payment payment = new Payment(connection, "abc", "");
        assertThat(payment.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment.isValid()).getMessage(), Errors.CODE_4[1]);
    }

    @Test
    public void testErrorOnLessThanZeroAmount() {
        Connection connection = getValidConnection();
        
        Payment payment = new Payment(connection, "abc", "-1.00");
        assertThat(payment.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment.isValid()).getMessage(), Errors.CODE_4[1]);
    }

    @Test
    public void testErrorOnMoreOrLessThan2DecimalPlacesForAmount() {
        Connection connection = getValidConnection();
        
        Payment payment1 = new Payment(connection, "abc", "0.00000000001");
        assertThat(payment1.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment1.isValid()).getMessage(), Errors.CODE_5[1]);

        Payment payment2 = new Payment(connection, "abc", "1");
        assertThat(payment2.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((payment2.isValid()).getMessage(), Errors.CODE_5[1]);
    }

    @Test
    public void testErrorOnInValidAmount() {
        Connection connection = getValidConnection();
        Payment payment = new Payment(connection, "abc", "A.BB");
        
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            payment.isValid();
        });

        assertTrue(exception.getMessage().contains("For input string"));
    }

    @Test
    public void testCreatePaymentClassOnValidInput() {
        Connection connection = getValidConnection();
        
        Payment payment = new Payment(connection, "abc", "1.00");
        assertEquals(payment.isValid(), null);
    }

    @Test
    public void testTokenHasRequiredPropertiesAndEncodedWithCorrectKey() {
        JSONObject decodedToken = getToken("abc", "1.00");
        
        assertTrue(decodedToken.containsKey("iat"));
        assertTrue(decodedToken.containsKey("exp"));
        assertTrue(decodedToken.containsKey("terminalID"));
        assertTrue(decodedToken.containsKey("orderID"));
        assertTrue(decodedToken.containsKey("amount"));
    }

    @Test
    public void testTokenHasCorrectValueEncoded() {
        JSONObject decodedToken = getToken("abc", "100.00");

        assertEquals(this.terminalId, decodedToken.get("terminalID"));
        assertEquals("abc", decodedToken.get("orderID"));
        assertEquals("100.00", decodedToken.get("amount"));
    }

    @Test
    public void testTokenHasCorrectValueForExtendedUsageEncoded() {
        Provider provider = new Provider("ob-lloyds", "123456", "12345678");
        User user = new User("test@test.com", "John", "Smith", "07000000");

        JSONObject decodedToken = getToken("abc", "100.00", user, provider);

        assertEquals(this.terminalId, decodedToken.get("terminalID"));
        assertEquals("abc", decodedToken.get("orderID"));
        assertEquals("100.00", decodedToken.get("amount"));
        assertEquals("ob-lloyds", decodedToken.get("bankID"));
        assertEquals("123456", decodedToken.get("sortCode"));
        assertEquals("12345678", decodedToken.get("accountNumber"));
        assertEquals("test@test.com", decodedToken.get("email"));
        assertEquals("John", decodedToken.get("firstName"));
        assertEquals("Smith", decodedToken.get("lastName"));
        assertEquals("07000000", decodedToken.get("contactNumber"));
    }
}