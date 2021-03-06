package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.entities.User;
import com.moviesApp.security.PasswordManager;
import com.moviesApp.service.UserService;
import com.moviesApp.validation.LoginValidator;
import com.moviesApp.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

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
        User foundUser;
        try {
            foundUser = userService.getUserByLogin(user.getLogin());
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }

        String from = "/home";
        String redirect = req.getParameter("redirectFrom");
        if (redirect != null) {
            if (!redirect.isEmpty()) {
                from = redirect;
            }
        }

        if (errors.isEmpty()) {
            if (foundUser == null) {
                errors.add("No such user");
                req.setAttribute("result", errors);
                req.setAttribute("logUser", user);
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            } else {
                if (!foundUser.isBanned()) {
                    if (!foundUser.getLogin().equals(user.getLogin()) ||
                            !PasswordManager.getSaltedHashPassword(user.getPassword()).equals(foundUser.getPassword())) {
                        errors.add("Wrong email or password");
                        req.setAttribute("result", errors);
                        req.setAttribute("logUser", user);
                        req.getRequestDispatcher("/index.jsp").forward(req, resp);
                    } else {
                        if (req.getParameter("regPage") != null) {
                            if (foundUser.isAdmin()) {
                                req.getSession().setAttribute("user", foundUser);
                                resp.sendRedirect("/admin");
                            } else {
                                req.getSession().setAttribute("user", foundUser);
                                resp.sendRedirect("/home");
                            }
                        } else {
                            req.getSession().setAttribute("user", foundUser);
                            resp.sendRedirect(from);
                        }
                    }
                } else {
                    errors.add("You are banned and can't login");
                    req.setAttribute("result", errors);
                    req.setAttribute("logUser", user);
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                }
            }
        } else {
            req.setAttribute("result", errors);
            req.setAttribute("logUser", user);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

}
