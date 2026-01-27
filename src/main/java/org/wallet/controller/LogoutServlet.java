package org.wallet.controller;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        resp.getWriter().write("{\"status\":\"logged out successfully\"}");
    }
}
