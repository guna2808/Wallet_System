package org.wallet.controller;

import org.wallet.dao.TransactionDAO;
import org.wallet.dao.UserDAO;
import org.wallet.util.DataSourceUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TransactionServlet extends HttpServlet {

    TransactionDAO transactionDAO = new TransactionDAO(DataSourceUtil.getDataSource());
    UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);
        String username = (String) session.getAttribute("username");

        String amountStr = req.getParameter("amount");
        String type = req.getParameter("type");

        if (amountStr == null || type == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"missing parameters\"}");
            return;
        }

        int userId = userDAO.getUserIdByUsername(username);

        double amount = Double.parseDouble(amountStr);
        transactionDAO.addTransaction(userId, amount, type);

        resp.getWriter().write("{\"status\":\"transaction added\"}");
    }
}


