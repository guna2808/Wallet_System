package org.wallet.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wallet.util.PasswordUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;

    private UserDAO userDAO;

    @BeforeEach
    void setup() throws Exception {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        ps = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(ps);

        userDAO = new UserDAO(dataSource);
    }

    @Test
    void registerUser_success() throws Exception {
        when(ps.executeUpdate()).thenReturn(1);

        boolean result = userDAO.register("guna", "password123");

        assertTrue(result);
    }

    @Test
    void registerUser_duplicateUsername() throws Exception {
        when(ps.executeUpdate()).thenThrow(new SQLException("Duplicate entry"));

        boolean result = userDAO.register("guna", "password123");

        assertFalse(result);
    }

    @Test
    void login_success() throws Exception {
        String hash = PasswordUtil.hashPassword("password123");

        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getString("password")).thenReturn(hash);

        boolean result = userDAO.login("guna", "password123");

        assertTrue(result);
    }

    @Test
    void login_wrongPassword() throws Exception {
        String hash = PasswordUtil.hashPassword("password123");

        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getString("password")).thenReturn(hash);

        boolean result = userDAO.login("guna", "wrong");

        assertFalse(result);
    }

    @Test
    void login_userNotFound() throws Exception {
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        boolean result = userDAO.login("ghost", "password");

        assertFalse(result);
    }

    @Test
    void getUserId_success() throws Exception {
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id")).thenReturn(7);

        int userId = userDAO.getUserIdByUsername("guna");

        assertEquals(7, userId);
    }

    @Test
    void getUserId_notFound() throws Exception {
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        int userId = userDAO.getUserIdByUsername("ghost");

        assertEquals(-1, userId);
    }
}
