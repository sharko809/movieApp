package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.entities.User;
import com.moviesApp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dsharko on 8/10/2016.
 */
public class AdminizeServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userID = null;
        try {
            userID = Long.valueOf(req.getParameter("userID"));
        } catch (NumberFormatException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing user ID", e);
            return;
        }
        String fromURL = req.getParameter("redirectFrom");
        User currentUser = (User) req.getSession().getAttribute("user");

        UserService userService = new UserService();
        User user = null;

        if (userID != null) {
            if (userID >= 1) {
                try {
                    user = userService.getUserByID(userID);
                } catch (SQLException e) {
                    ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
                    return;
                }
                if (userID.longValue() != currentUser.getId().longValue()) {
                    if (user != null) {
                        if (user.isAdmin()) {
                            user.setAdmin(false);
                        } else {
                            user.setAdmin(true);
                        }
                        try {
                            userService.updateUser(user);
                        } catch (SQLException e) {
                            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing user ID", e);
                            return;
                        }
                        resp.sendRedirect(fromURL);
                    } else {
                        req.setAttribute("errorDetails", "User not found");
                        req.getRequestDispatcher("/error").forward(req, resp);
                    }
                } else {
                    req.setAttribute("errorDetails", "Can't change your own admin state");
                    req.getRequestDispatcher("/error").forward(req, resp);
                }
            }
        } else {
            req.setAttribute("errorDetails", "Can't identify you");
            req.getRequestDispatcher("/error").forward(req, resp);
        }

    }
}
