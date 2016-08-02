package com.moviesApp.controller;

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
        System.out.println("DOING POST");

        User user = new User();
        user.setLogin(userLogin);
        user.setPassword(password);

        Validator validator = new LoginValidator();
        List<String> errors = validator.validate(user);

        UserService userService = new UserService();
        User searchResult = userService.getUserByLogin(userLogin);

        if (errors.isEmpty()) {
            if (searchResult == null) {
                System.out.println("no such user");
                errors.add("No such user");
                req.getSession().setAttribute("result", errors);
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            } else {
                if (!searchResult.getLogin().equals(userLogin) |
                        !PasswordManager.getSaltedHashPassword(password).equals(searchResult.getPassword())) {
                    System.out.println("wrong email or pass");
                    errors.add("Wrong email or password");
                    req.getSession().setAttribute("result", errors);
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                } else {
                    System.out.println("OK");
                    req.getSession().setAttribute("user", searchResult);
                    resp.sendRedirect(req.getContextPath() + "/home");
                }
            }
        } else {
            req.getSession().setAttribute("result", errors);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }


    }

}
