package com.moviesApp.controller;

import com.moviesApp.entities.User;
import com.moviesApp.security.PasswordManager;
import com.moviesApp.service.UserService;
import com.moviesApp.validation.RegistrationValidator;
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
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String userName = req.getParameter("newUserName");
        String userLogin = req.getParameter("newUserLogin");
        String password = req.getParameter("newUserPassword");

        User user = new User();
        user.setName(userName);
        user.setLogin(userLogin);
        user.setPassword(password);

        Validator validator = new RegistrationValidator();
        List<String> errors = validator.validate(user);

        if (errors.isEmpty()) {
            String encodedPassword = PasswordManager.getSaltedHashPassword(password);
            UserService userService = new UserService();
            Long userId = userService.createUser(userName, userLogin, encodedPassword);
            if (userId < 0) {
                errors.add("User is not created. Later I'll put response from the server here.");
            }
            if (errors.isEmpty()) {
                req.getSession().setAttribute("result", "User " + userName + " successfully created.");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        } else {
            req.getSession().setAttribute("result", errors);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }

    }
}
