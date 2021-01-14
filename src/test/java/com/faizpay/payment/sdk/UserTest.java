package com.faizpay.payment.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void testErrorOnCreateUserWithInvalidEmail() {
        User user = new User("test@test1com", "John", "Smith", "07000000");
        assertThat(user.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((user.isValid()).getMessage(), Errors.CODE_11[1]);
    }

    @Test
    public void testErrorOnCreateUserWithInvalidEmail2() {
        User user = new User("testtest1.com", "John", "Smith", "07000000");
        assertThat(user.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((user.isValid()).getMessage(), Errors.CODE_11[1]);
    }

    @Test
    public void testErrorOnCreateFieldMoreThan255Characters() {
        User user1 = new User("test@test.com", "a".repeat(256), "Smith", "07000000");
        assertThat(user1.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((user1.isValid()).getMessage(), Errors.CODE_12[1]);

        User user2 = new User("test@test.com", "john", "a".repeat(256), "07000000");
        assertThat(user2.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((user2.isValid()).getMessage(), Errors.CODE_13[1]);

        User user3 = new User("test@test.com", "john", "smith", "a".repeat(256));
        assertThat(user3.isValid(), instanceOf(ErrorHandler.class));
        assertEquals((user3.isValid()).getMessage(), Errors.CODE_14[1]);
    }

    @Test
    public void testCreateUserOnValidInput() {
        User user = new User("test@test.com", "john", "smith", "07497123123");
        assertEquals(user.isValid(), null);
        assertEquals(user.getEmail(), "test@test.com");
        assertEquals(user.getFirstName(), "john");
        assertEquals(user.getLastName(), "smith");
        assertEquals(user.getContactNumber(), "07497123123");
    }
}