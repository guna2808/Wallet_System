package org.wallet.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wallet.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    UserDAO userDAO = new UserDAO();
    Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");

        String username = null;
        String password = null;

        try {
            if (req.getContentType() != null &&
                    req.getContentType().contains("application/json")) {

                Map<String, String> body =
                        gson.fromJson(req.getReader(), Map.class);

                if (body != null) {
                    username = body.get("username");
                    password = body.get("password");
                }

            } else {
                username = req.getParameter("username");
                password = req.getParameter("password");
            }

            if (username != null) username = username.trim();
            if (password != null) password = password.trim();

            if (username == null || password == null ||
                    username.isEmpty() || password.isEmpty()) {

                logger.warn("Login failed: missing credentials");

                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter()
                        .write("{\"error\":\"username and password required\"}");
                return;
            }

            boolean authenticated = userDAO.login(username, password);

            if (authenticated) {

                HttpSession oldSession = req.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate();
                }

                HttpSession newSession = req.getSession(true);
                newSession.setAttribute("username", username);

                logger.info("Login successful for user: {}", username);

                resp.getWriter()
                        .write("{\"status\":\"login success\"}");

            } else {
                logger.warn("Invalid login attempt for user: {}", username);

                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter()
                        .write("{\"error\":\"invalid credentials\"}");
            }

        } catch (Exception e) {
            logger.error("Login error", e);

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter()
                    .write("{\"error\":\"internal server error\"}");
        }
    }
}
