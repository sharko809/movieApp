package com.moviesApp.controller;

import com.moviesApp.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

        UserService userService = new UserService();
        Long userId = userService.createUser(userName, userLogin, password);// TODO ENCRYPT THIS!!!
        if (userId > 0) {
            req.getSession().setAttribute("result", "User " + userName + " successfully created.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
            req.getSession().setAttribute("result", "User is not created. Later I'll put response from the server here.");
            requestDispatcher.forward(req, resp);
        }

    }
}
