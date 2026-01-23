package org.wallet.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    void setup() {
        userDAO = new UserDAO();
    }

    private String uniqueUsername() {
        return "user_" + UUID.randomUUID();
    }

    @Test
    void registerUser_success() {
        String username = uniqueUsername();

        boolean result = userDAO.register(username, "password123");
        assertTrue(result);
    }

    @Test
    void registerUser_duplicateUsername() {
        String username = uniqueUsername();

        userDAO.register(username, "password123");
        boolean result = userDAO.register(username, "password123");

        assertFalse(result);
    }

    @Test
    void login_success() {
        String username = uniqueUsername();

        userDAO.register(username, "password123");
        boolean result = userDAO.login(username, "password123");

        assertTrue(result);
    }

    @Test
    void login_wrongPassword() {
        String username = uniqueUsername();

        userDAO.register(username, "password123");
        boolean result = userDAO.login(username, "wrong");

        assertFalse(result);
    }

    @Test
    void login_userNotFound() {
        boolean result = userDAO.login("ghost_" + UUID.randomUUID(), "test");
        assertFalse(result);
    }

    @Test
    void getUserId_success() {
        String username = uniqueUsername();

        userDAO.register(username, "password123");
        int userId = userDAO.getUserIdByUsername(username);

        assertTrue(userId > 0);
    }

    @Test
    void getUserId_notFound() {
        int userId = userDAO.getUserIdByUsername("ghost_" + UUID.randomUUID());
        assertEquals(-1, userId);
    }
}
