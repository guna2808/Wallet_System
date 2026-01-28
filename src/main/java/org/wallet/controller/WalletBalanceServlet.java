package org.wallet.controller;

import com.google.gson.Gson;
import org.wallet.dao.TransactionDAO;
import org.wallet.dao.UserDAO;
import org.wallet.dto.BalanceResponseDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WalletBalanceServlet extends HttpServlet {

     UserDAO userDAO = new UserDAO();
     TransactionDAO transactionDAO = new TransactionDAO();
     Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);
        String username = (String) session.getAttribute("username");

        int userId = userDAO.getUserIdByUsername(username);

        if (userId == -1) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"user not found\"}");
            return;
        }

        double balance = transactionDAO.getWalletBalance(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("balance", balance);

        BalanceResponseDTO dto = new BalanceResponseDTO(username, balance);

        resp.getWriter().write(gson.toJson(dto));

    }
}
