package com.moviesApp.controller;

import com.moviesApp.entities.User;
import com.moviesApp.service.UserService;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userID = Long.valueOf(req.getParameter("userID"));
        String fromURL = req.getParameter("redirectFrom");
        User currentUser = (User) req.getSession().getAttribute("user");

        UserService userService = new UserService();
        User user = null;

        if (userID != null) {
            if (userID >= 1) {
                try {
                    user = userService.getUserByID(userID);
                } catch (SQLException e) {
                    e.printStackTrace();
                    req.getSession().setAttribute("errorDetails", e);
                    resp.sendRedirect(req.getContextPath() + "/error");
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
                            e.printStackTrace();
                            req.getSession().setAttribute("errorDetails", e);
                            resp.sendRedirect(req.getContextPath() + "/error");
                            return;
                        }
                        resp.sendRedirect(fromURL);
                    } else {
                        req.getSession().setAttribute("errorDetails", "User not found");
                        resp.sendRedirect(req.getContextPath() + "/error");
                    }
                } else {
                    req.getSession().setAttribute("errorDetails", "Can't change your own admin state");
                    resp.sendRedirect(req.getContextPath() + "/error");
                }
            }
        } else {
            req.getSession().setAttribute("errorDetails", "Can't identify you");
            resp.sendRedirect(req.getContextPath() + "/error");
        }

    }
}
