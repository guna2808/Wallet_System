package org.wallet.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wallet.dao.TransactionDAO;
import org.wallet.dao.UserDAO;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GetTransactionsServletTest {

    private GetTransactionsServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private UserDAO userDAO;
    private TransactionDAO transactionDAO;
    private StringWriter writer;

    @BeforeEach
    void setup() throws Exception {
        servlet = new GetTransactionsServlet();
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
        when(transactionDAO.getTransactionsByUser(1))
                .thenReturn(Collections.emptyList());

        writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    void getTransactions_success() throws Exception {
        servlet.doGet(request, response);
        assertTrue(writer.toString().contains("["));
    }
}
