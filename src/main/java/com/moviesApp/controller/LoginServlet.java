package com.moviesApp.controller;

import com.moviesApp.entities.User;

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

    private final String LOGIN = "login";
    private final String PWD = "pwd";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String userName = req.getParameter("userName");
        String password = req.getParameter("userPassword");
        System.out.println("DOING POST");

        if (LOGIN.equals(userName) && PWD.equals(password)) {
            System.out.println("OK");
            User user = new User();
            user.setName("logged in user. congrats!");
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            System.out.println("no such user");
            req.getSession().setAttribute("errorDetails", "Wrong username or password");
            resp.sendRedirect(req.getContextPath() + "/error");
        }

    }

}
