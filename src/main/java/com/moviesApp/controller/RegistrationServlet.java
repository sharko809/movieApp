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
        String userRole = req.getParameter("newUserRole");// TODO watch this

        User user = new User();
        user.setName(userName);
        user.setLogin(userLogin);
        user.setPassword(password);
        if (userRole != null) {
            user.setAdmin(true);// this is for the default user registration process
        } else {
            user.setAdmin(false);
        }


        Validator validator = new RegistrationValidator();
        List<String> errors = validator.validate(user);// TODO think about role validation

        if (errors.isEmpty()) {
            UserService userService = new UserService();
            if (userService.getUserByLogin(userLogin) == null) {
                String encodedPassword = PasswordManager.getSaltedHashPassword(password);
                Long userId = userService.createUser(userName, userLogin, encodedPassword, user.getAdmin());
                req.setAttribute("result", "User " + userName + " successfully created.");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            } else {
                errors.add("User with such login already exists");
                req.setAttribute("result", errors);
                req.setAttribute("regUser", user);
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("result", errors);
            req.setAttribute("regUser", user);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }

    }
}
