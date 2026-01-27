package org.wallet.controller;

import org.wallet.dao.UserDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null ||
                username.isBlank() || password.isBlank()) {

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"username and password required\"}");
            return;
        }

        boolean registered = userDAO.register(username, password);

        if (registered) {
            resp.getWriter().write("{\"status\":\"registered successfully\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().write("{\"error\":\"username already exists\"}");
        }
    }
}
