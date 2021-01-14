package com.faizpay.payment.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.jupiter.api.Test;

public class NumberFormatterTest {

    @Test
    public void testFormatNumberOnValidStringInput() {
        String result = NumberFormatter.formatNumber("20.0451");
        assertEquals(result, "20.05");
    }

    @Test
    public void testFormatNumberOnNumberInput() {
        String result = NumberFormatter.formatNumber("20.0451");
        assertEquals(result, "20.05");
    }

    @Test
    public void testvalidateTwoDecimalsOnCorrectInput() {
        boolean result = NumberFormatter.validateTwoDecimals("20.00");
        assertTrue(result);
    }

    @Test
    public void testvalidateTwoDecimalsOnIncorrectInputDecimals() {
        boolean result = NumberFormatter.validateTwoDecimals("20.0000000");
        assertFalse(result);
    }

    @Test
    public void testvalidateTwoDecimalsOnIncorrectInputDecimalsPartTwo() {
        boolean result = NumberFormatter.validateTwoDecimals("20.0000786");
        assertFalse(result);
    }
    
}
