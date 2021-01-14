package com.faizpay.payment.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.jupiter.api.Test;

public class ProviderTest {
    @Test
    public void testErrorOnInvalidSortCode() {
        Provider provider = new Provider("ob-lloyds", "12345", "12345678");
        assertThat(provider.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((provider.isValid()).getMessage(), Errors.CODE_7[1]);
    }

    @Test
    public void testErrorOnInvalidAccountNumber() {
        Provider provider = new Provider("ob-lloyds", "123456", "1234567899");
        assertThat(provider.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((provider.isValid()).getMessage(), Errors.CODE_8[1]);
    }

    @Test
    public void testErrorOnEmptyProviderButSortCodeAndAccountGiven() {
        Provider provider = new Provider("", "123456", "12345678");
        assertThat(provider.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((provider.isValid()).getMessage(), Errors.CODE_9[1]);
    }

    @Test
    public void testErrorOnSortCodeOrAccountNumberIsMissing() {
        Provider provider1 = new Provider("ob-lloyds", "", "12345678");
        assertThat(provider1.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((provider1.isValid()).getMessage(), Errors.CODE_10[1]);

        Provider provider2 = new Provider("ob-lloyds", "123456", "");
        assertThat(provider2.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((provider2.isValid()).getMessage(), Errors.CODE_10[1]);
    }

    @Test
    public void createNewProviderWithProviderAndAccountDetails() {
        Provider provider = new Provider("ob-lloyds","123456","12345678");
        assertEquals(provider.isValid(), null);
        assertEquals(provider.getProviderId(), "ob-lloyds");
        assertEquals(provider.getSortCode(), "123456");
        assertEquals(provider.getAccountNumber(), "12345678");
    }
}