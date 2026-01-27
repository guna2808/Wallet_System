package org.wallet.controller;

import com.google.gson.Gson;
import org.wallet.dao.TransactionDAO;
import org.wallet.dao.UserDAO;
import org.wallet.dto.TransactionResponseDTO;
import org.wallet.model.Transaction;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetTransactionsServlet extends HttpServlet {

    TransactionDAO transactionDAO = new TransactionDAO();
    UserDAO userDAO = new UserDAO();
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

        List<Transaction> transactions =
                transactionDAO.getTransactionsByUser(userId);

        List<TransactionResponseDTO> response = new ArrayList<>();

        for (Transaction t : transactions) {
            response.add(
                    new TransactionResponseDTO(
                            t.getAmount(),
                            t.getType(),
                            t.getCreatedAt().toString()
                    )
            );
        }

        resp.getWriter().write(gson.toJson(response));
    }
}
