package com.moviesApp.controller;

import com.moviesApp.UrlParametersManager;
import com.moviesApp.entities.User;
import com.moviesApp.security.PasswordManager;
import com.moviesApp.service.UserService;
import com.moviesApp.validation.LoginValidator;
import com.moviesApp.validation.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by dsharko on 8/1/2016.
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String userLogin = req.getParameter("userLogin");
        String password = req.getParameter("userPassword");

        User user = new User();
        user.setLogin(userLogin);
        user.setPassword(password);

        Validator validator = new LoginValidator();
        List<String> errors = validator.validate(user);

        UserService userService = new UserService();
        User searchResult = userService.getUserByLogin(userLogin);

        String from = "/home";// TODO think of it. But I prefer let it be default.
        String redirect = req.getParameter("redirectFrom");
        if (redirect != null) {
            if (!redirect.isEmpty()) {
                from = redirect;
            }
        }

        if (errors.isEmpty()) {
            if (searchResult == null) {
                errors.add("No such user");
                req.setAttribute("result", errors);
                req.setAttribute("logUser", user);
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            } else {
                if (!searchResult.getLogin().equals(userLogin) |
                        !PasswordManager.getSaltedHashPassword(password).equals(searchResult.getPassword())) {
                    errors.add("Wrong email or password");
                    req.setAttribute("result", errors);
                    req.setAttribute("logUser", user);
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                } else {
                    if (req.getParameter("regPage") != null) {
                        if (searchResult.getAdmin()) {
                            req.getSession().setAttribute("user", searchResult);
                            resp.sendRedirect(req.getContextPath() + "/admin");
                        } else {
                            req.getSession().setAttribute("user", searchResult);
                            resp.sendRedirect(req.getContextPath() + "/home");
                        }
                    } else {
                        req.getSession().setAttribute("user", searchResult);
                        resp.sendRedirect(from);
                    }
                }
            }
        } else {
            req.setAttribute("result", errors);
            req.setAttribute("logUser", user);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }


    }

}
