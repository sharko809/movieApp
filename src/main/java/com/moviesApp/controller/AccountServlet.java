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
import java.util.Optional;

/**
 * Created by dsharko on 8/10/2016.
 */
public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = 0L;

        Map<String, List<String>> urlParams = UrlParametersManager.getUrlParams(req.getQueryString());

        if (urlParams != null) {

            Optional<List<String>> value = urlParams.entrySet().stream()
                    .filter(params -> "id".equals(params.getKey()))
                    .map(Map.Entry::getValue)
                    .findFirst();

            if (value.isPresent()) {
                if (!value.get().isEmpty() && value.get().size() == 1) {
                    try {
                        userId = Long.parseLong(value.get().get(0));
                    } catch (NumberFormatException e) {
                        req.setAttribute("errorDetails", "Invalid request URL");
                        req.getRequestDispatcher("/resources/view/error.jsp").forward(req, resp);
                    }
                }
            }

        } else {
            req.setAttribute("errorDetails", "Invalid request url");
            req.getRequestDispatcher("/error").forward(req, resp);
        }

        UserService userService = new UserService();
        User user = null;
        if (userId != null) {
            if (userId >= 1) {
                try {
                    user = userService.getUserByID(userId);
                } catch (SQLException e) {
                    e.printStackTrace();
                    req.setAttribute("errorDetails", "Invalid request url");
                    req.getRequestDispatcher("/error").forward(req, resp);
                    return;
                }
            }
        }

        req.setAttribute("thisUser", user);
        req.getRequestDispatcher("/resources/views/account.jsp").forward(req, resp);
    }

}
