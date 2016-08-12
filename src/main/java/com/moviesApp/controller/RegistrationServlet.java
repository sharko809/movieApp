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
import java.sql.SQLException;
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
        String userRole = req.getParameter("newUserRole");// TODO watch this.
        // Any sick bastard can become admin just by simple POST request. I'll make another reg servlet specially for admin


        User user = new User();
        user.setName(userName);
        user.setLogin(userLogin);
        user.setPassword(password);
        if (userRole != null) {
            user.setAdmin(true);// this is for the default user registration process
        } else {
            user.setAdmin(false);// TODO think may be separate with admin reg
        }


        Validator validator = new RegistrationValidator();
        List<String> errors = validator.validate(user);// TODO think about role validation

        if (errors.isEmpty()) {
            UserService userService = new UserService();
            User userToCheck = null;
            try {
                userToCheck = userService.getUserByLogin(user.getLogin());
            } catch (SQLException e) {
                e.printStackTrace();
                req.getSession().setAttribute("errorDetails", e);
                resp.sendRedirect(req.getContextPath() + "/error");
                return;
            }
            if (userToCheck == null) {
                String encodedPassword = PasswordManager.getSaltedHashPassword(user.getPassword());
                try {
                    userService.createUser(user.getName(), user.getLogin(), encodedPassword, user.isAdmin());
                } catch (SQLException e) {
                    e.printStackTrace();
                    req.getSession().setAttribute("errorDetails", e);
                    resp.sendRedirect(req.getContextPath() + "/error");
                    return;
                }
                req.setAttribute("result", "User " + user.getName() + " successfully created.");
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
