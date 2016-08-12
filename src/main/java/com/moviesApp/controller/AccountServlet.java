package com.moviesApp.controller;

import com.moviesApp.UrlParametersManager;
import com.moviesApp.entities.User;
import com.moviesApp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by dsharko on 8/10/2016.
 */
public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Long userId[] = new Long[1];

        Map<String, List<String>> urlParams = UrlParametersManager.getUrlParams(req.getQueryString());

        if (urlParams != null) {
            urlParams.forEach((key, value) -> {
                if ("id".equals(key)) {
                    userId[0] = Long.valueOf(value.get(0));
                }
            });
        } else {
            req.getSession().setAttribute("errorDetails", "Invalid request url");
            resp.sendRedirect(req.getContextPath() + "/error");
            return;
        }

        UserService userService = new UserService();
        User user = null;
        if (userId[0] != null) {
            if (userId[0] >= 1) {
                try {
                    user = userService.getUserByID(userId[0]);
                } catch (SQLException e) {
                    e.printStackTrace();
                    req.getSession().setAttribute("errorDetails", "Invalid request url");
                    resp.sendRedirect(req.getContextPath() + "/error");
                    return;
                }
            }
        }

        req.setAttribute("thisUser", user);
        req.getRequestDispatcher("/resources/views/account.jsp").forward(req, resp);
    }
}
