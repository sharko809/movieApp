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
public class BanServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userID = 0L;
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
                        if (user.isBanned()) {
                            user.setBanned(false);
                        } else {
                            user.setBanned(true);
                        }
                        try {
                            userService.updateUser(user);
                        } catch (SQLException e) {
                            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
                            return;
                        }
                        String fromMoviePage = req.getParameter("fromMovie");
                        if (fromMoviePage != null) {
                            String movieIdParam = req.getParameter("movieID");
                            if (movieIdParam != null) {
                                Long movieID;
                                try {
                                    movieID = Long.valueOf(movieIdParam);
                                } catch (NumberFormatException e) {
                                    ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing movieID", e);
                                    return;
                                }
                                resp.sendRedirect("editmovie?movieID=" + movieID);
                                return;
                            }
                        }
                        resp.sendRedirect(fromURL);
                    } else {
                        req.setAttribute("errorDetails", "User not found");
                        req.getRequestDispatcher("/error").forward(req, resp);
                    }
                } else {
                    req.setAttribute("errorDetails", "Can't ban yourself");
                    req.getRequestDispatcher("/error").forward(req, resp);
                }
            }
        } else {
            req.setAttribute("errorDetails", "Can't identify you");
            req.getRequestDispatcher("/error").forward(req, resp);
        }

    }
}
