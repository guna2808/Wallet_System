package org.wallet.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wallet.dao.TransactionDAO;
import org.wallet.dao.UserDAO;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

class TransactionServletTest {

    private TransactionServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private UserDAO userDAO;
    private TransactionDAO transactionDAO;
    private StringWriter writer;

    @BeforeEach
    void setup() throws Exception {
        servlet = new TransactionServlet();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        userDAO = mock(UserDAO.class);
        transactionDAO = mock(TransactionDAO.class);

        servlet.userDAO = userDAO;
        servlet.transactionDAO = transactionDAO;

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("guna");

        writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    void addTransaction_success() throws Exception {
        when(request.getParameter("amount")).thenReturn("100");
        when(request.getParameter("type")).thenReturn("credit");
        when(userDAO.getUserIdByUsername("guna")).thenReturn(1);

        servlet.doPost(request, response);

        verify(transactionDAO).addTransaction(1, 100.0, "credit");
    }
}
