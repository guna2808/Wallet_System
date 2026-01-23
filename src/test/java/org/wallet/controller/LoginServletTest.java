package org.wallet.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wallet.dao.UserDAO;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LoginServletTest {

    private LoginServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private UserDAO userDAO;
    private StringWriter writer;

    @BeforeEach
    void setup() throws Exception {
        servlet = new LoginServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        userDAO = mock(UserDAO.class);

        servlet.userDAO = userDAO;

        writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    void login_success() throws Exception {
        when(request.getParameter("username")).thenReturn("guna");
        when(request.getParameter("password")).thenReturn("guna2808");
        when(request.getSession(true)).thenReturn(session);
        when(userDAO.login("guna", "guna2808")).thenReturn(true);

        servlet.doPost(request, response);

        assertTrue(writer.toString().contains("login success"));
    }

    @Test
    void login_invalidCredentials() throws Exception {
        when(request.getParameter("username")).thenReturn("guna");
        when(request.getParameter("password")).thenReturn("wrong");
        when(userDAO.login("guna", "wrong")).thenReturn(false);

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void login_missingParams() throws Exception {
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("password")).thenReturn(null);

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
