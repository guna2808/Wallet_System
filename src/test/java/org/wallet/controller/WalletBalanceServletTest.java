package org.wallet.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wallet.dao.TransactionDAO;
import org.wallet.dao.UserDAO;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WalletBalanceServletTest {

    private WalletBalanceServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private UserDAO userDAO;
    private TransactionDAO transactionDAO;
    private StringWriter writer;

    @BeforeEach
    void setup() throws Exception {
        servlet = new WalletBalanceServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        userDAO = mock(UserDAO.class);
        transactionDAO = mock(TransactionDAO.class);

        servlet.userDAO = userDAO;
        servlet.transactionDAO = transactionDAO;

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("guna");
        when(userDAO.getUserIdByUsername("guna")).thenReturn(1);
        when(transactionDAO.getWalletBalance(1)).thenReturn(300.0);

        writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    void getBalance_success() throws Exception {
        servlet.doGet(request, response);
        assertTrue(writer.toString().contains("300.0"));
    }
}
