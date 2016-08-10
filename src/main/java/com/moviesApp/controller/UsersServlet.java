package com.moviesApp.controller;

import com.moviesApp.entities.User;
import com.moviesApp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by dsharko on 8/10/2016.
 */
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();

        // TODO add pagination!!!


        Object sortedAttribute = req.getSession().getAttribute("sortedUsers");
        req.getSession().removeAttribute("sortedUsers");

        if (sortedAttribute instanceof Set) {
            Set<User> sortedUsers = (Set<User>) sortedAttribute;
            req.getSession().setAttribute("users", sortedUsers);
            req.getRequestDispatcher("/resources/views/users.jsp").forward(req, resp);
        }

        req.setAttribute("users", users);
        req.getRequestDispatcher("/resources/views/users.jsp").forward(req, resp);
    }
}
