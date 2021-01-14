package com.faizpay.payment.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.jupiter.api.Test;

public class ErrorHandlerTest {
    @Test
    public void testGetCode() {
        ErrorHandler error = new ErrorHandler(Errors.CODE_1);
        assertThat(error, instanceOf(ErrorHandler.class));
        assertEquals(error.getCode(), Errors.CODE_1[0]);
    }

    @Test
    public void testGetMessage() {
        ErrorHandler error = new ErrorHandler(Errors.CODE_1);
        assertThat(error, instanceOf(ErrorHandler.class));
        assertEquals(error.getMessage(), Errors.CODE_1[1]);
    }
}
