package com.moviesApp.controller;

import com.moviesApp.entities.User;
import com.moviesApp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

        UserService userService = new UserService();
        User searchResult = null;
        searchResult = userService.getUserByLogin(userLogin);// TODO do I REALLY need all of this stuff? mb just login?

        if (searchResult!= null && searchResult.getLogin().equals(userLogin) && searchResult.getPassword().equals(password)) {
            System.out.println("OK");
            req.getSession().setAttribute("user", searchResult);
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            System.out.println("no such user");
            req.getSession().setAttribute("errorDetails", "Wrong username or password");
            resp.sendRedirect(req.getContextPath() + "/error");
        }

    }

}
