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
 * Created by dsharko on 8/12/2016.
 */
public class UpdateAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String userLogin = req.getParameter("userLogin");
        String userPassword = req.getParameter("userPassword");
        String from = req.getParameter("redirectFrom");

        Object results = req.getSession().getAttribute("result");
        if (results != null) {
            req.getSession().removeAttribute("result");
        }

        User currentUser = (User) req.getSession().getAttribute("user");
        User user = new User();
        user.setId(currentUser.getId());
        user.setName(userName);
        user.setLogin(userLogin);
        if (userPassword == null || userPassword.isEmpty()) {
            user.setPassword(currentUser.getPassword());
        } else {
            String newPassword = PasswordManager.getSaltedHashPassword(userPassword);
            user.setPassword(newPassword);
        }
        user.setAdmin(currentUser.isAdmin());
        user.setBanned(currentUser.isBanned());

        Validator validator = new RegistrationValidator();
        List<String> errors = validator.validate(user);

        if (errors.isEmpty()) {
            UserService userService = new UserService();
            try {
                userService.updateUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
                req.getSession().setAttribute("errorDetails", "Invalid request url");
                resp.sendRedirect(req.getContextPath() + "/error");
                return;
            }
            req.getSession().setAttribute("user", user);
            req.setAttribute("result", "Update OK");// TODO it shouldn't go through all session
            resp.sendRedirect(from);
        } else {
            req.setAttribute("result", errors);// TODO it shouldn't go through all session
            resp.sendRedirect(from);
        }


    }
}
