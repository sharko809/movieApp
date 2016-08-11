package com.moviesApp.controller;

import com.moviesApp.entities.User;
import com.moviesApp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dsharko on 8/10/2016.
 */
public class BanServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userID = Long.valueOf(req.getParameter("userID"));
        String fromURL = req.getParameter("redirectFrom");
        User currentUser = (User) req.getSession().getAttribute("user");

        UserService userService = new UserService();
        User user = null;
        if (userID != null) {
            if (userID >= 1) {
                user = userService.getUserByID(userID);
                if (userID.longValue() != currentUser.getId().longValue()) {
                    if (user != null) {
                        if (user.isBanned()) {
                            user.setBanned(false);
                        } else {
                            user.setBanned(true);
                        }
                        userService.updateUser(user);
                        resp.sendRedirect(fromURL);
                    } else {
                        req.getSession().setAttribute("errorDetails", "User not found");
                        resp.sendRedirect(req.getContextPath() + "/error");
                    }
                } else {
                    req.getSession().setAttribute("errorDetails", "Can't ban yourself");
                    resp.sendRedirect(req.getContextPath() + "/error");
                }
            }
        } else {
            req.getSession().setAttribute("errorDetails", "Can't identify you");
            resp.sendRedirect(req.getContextPath() + "/error");
        }

    }
}
