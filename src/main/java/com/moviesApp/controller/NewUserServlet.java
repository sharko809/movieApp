package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.entities.User;
import com.moviesApp.security.PasswordManager;
import com.moviesApp.service.UserService;
import com.moviesApp.validation.RegistrationValidator;
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

/**
 * Created by dsharko on 8/17/2016.
 */
public class NewUserServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/resources/views/adminnewuser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("newUserName");
        String userLogin = req.getParameter("newUserLogin");
        String password = req.getParameter("newUserPassword");
        String isAdmin = req.getParameter("isAdmin");

        User user = new User();
        user.setName(userName);
        user.setLogin(userLogin);
        user.setPassword(password);
        if (isAdmin != null) {
            if ("on".equals(isAdmin)) {
                user.setAdmin(true);
            } else {
                user.setAdmin(false);
            }
        } else {
            user.setAdmin(false);
        }

        Validator validator = new RegistrationValidator();
        List<String> errors = validator.validate(user);

        if (errors.isEmpty()) {
            UserService userService = new UserService();
            User userToCheck = null;
            try {
                userToCheck = userService.getUserByLogin(user.getLogin());
            } catch (SQLException e) {
                ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
                return;
            }
            if (userToCheck == null) {
                String encodedPassword = PasswordManager.getSaltedHashPassword(user.getPassword());
                try {
                    userService.createUser(user.getName(), user.getLogin(), encodedPassword, user.isAdmin());
                } catch (SQLException e) {
                    ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
                    return;
                }
                resp.sendRedirect("/admin/newuser");
            } else {
                errors.add("User with such login already exists");
                req.setAttribute("result", errors);
                req.setAttribute("regUser", user);
                req.getRequestDispatcher("/resources/views/adminnewuser.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("result", errors);
            req.setAttribute("regUser", user);
            req.getRequestDispatcher("/resources/views/adminnewuser.jsp").forward(req, resp);
        }


    }
}
