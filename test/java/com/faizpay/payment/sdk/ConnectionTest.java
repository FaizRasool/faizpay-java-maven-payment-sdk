package com.faizpay.payment.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.jupiter.api.Test;

public class ConnectionTest {
    @Test
    public void testInvalidIDForCreateConnection() {
        String id = "aa";
        String secret = "f9fd95d5-2acb-4643-b3fb-b16999f37175";
        Connection connection = new Connection(id, secret);
        assertThat(connection.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((connection.isValid()).getMessage(), Errors.CODE_1[1]);
    }

    @Test
    public void testInvalidSecretForCreateConnection() {
        String id = "f9fd95d5-2acb-4643-b3fb-b16999f37175";
        String secret = "aa";
        Connection connection = new Connection(id, secret);
        assertThat(connection.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((connection.isValid()).getMessage(), Errors.CODE_2[1]);
    }

    @Test
    public void testInvalidSecretAndIdForCreateConnection() {
        String id = "aa";
        String secret = "aa";
        Connection connection = new Connection(id, secret);
        assertThat(connection.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((connection.isValid()).getMessage(), Errors.CODE_1[1]);
    }

    @Test
    public void testCreateConnectionForValidIDAndSecret() {
        String id = "1536bc14-9273-460e-9711-7e96733616fe";
        String secret = "f9fd95d5-2acb-4643-b3fb-b16999f37175";
        Connection connection = new Connection(id, secret);
        assertEquals(connection.isValid(), null);
        assertEquals(connection.getTerminalId(), id);
        assertEquals(connection.getTerminalSecret(), secret);
    }
}