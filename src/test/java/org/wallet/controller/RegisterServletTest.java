package org.wallet.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wallet.dao.UserDAO;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RegisterServletTest {

    private RegisterServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserDAO userDAO;
    private StringWriter writer;

    @BeforeEach
    void setup() throws Exception {
        servlet = new RegisterServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        userDAO = mock(UserDAO.class);

        servlet.userDAO = userDAO;

        writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    void register_success() throws Exception {
        when(request.getParameter("username")).thenReturn("guna");
        when(request.getParameter("password")).thenReturn("guna2808");
        when(userDAO.register("guna", "guna2808")).thenReturn(true);

        servlet.doPost(request, response);

        assertTrue(writer.toString().contains("registered"));
    }

    @Test
    void register_duplicateUser() throws Exception {
        when(request.getParameter("username")).thenReturn("guna");
        when(request.getParameter("password")).thenReturn("guna2808");
        when(userDAO.register("guna", "guna2808")).thenReturn(false);

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CONFLICT);
    }
}
