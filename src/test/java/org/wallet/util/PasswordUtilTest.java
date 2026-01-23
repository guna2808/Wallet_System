package org.wallet.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    void hashPassword_notNull() {
        String hash = PasswordUtil.hashPassword("password123");
        assertNotNull(hash);
    }

    @Test
    void verifyPassword_success() {
        String hash = PasswordUtil.hashPassword("password123");

        assertTrue(
                PasswordUtil.verifyPassword("password123", hash)
        );
    }

    @Test
    void verifyPassword_failure() {
        String hash = PasswordUtil.hashPassword("password123");

        assertFalse(
                PasswordUtil.verifyPassword("wrong", hash)
        );
    }
}
