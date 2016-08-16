package com.moviesApp.controller;

import com.moviesApp.entities.User;
import com.moviesApp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dsharko on 8/10/2016.
 */
public class SortUsersServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();
        List<User> users = null;
        try {
            users = userService.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("errorDetails", e);
            req.getRequestDispatcher("/error").forward(req, resp);
            return;
        }

        if (users == null) {
            return;
        }
        
        String fromULR = req.getParameter("redirectFrom");
        String sortBy = req.getParameter("sortBy");

        switch (sortBy) {
            case "id":
                users.sort((User u1, User u2) -> u1.getId().compareTo(u2.getId()));
                break;
            case "login":
                users.sort((User u1, User u2) -> u1.getLogin().compareTo(u2.getLogin()));
                break;
            case "userName":
                users.sort((User u1, User u2) -> u1.getName().compareTo(u2.getName()));
                break;
            case "admin":
                users.sort((User u1, User u2) -> u1.isAdmin().compareTo(u2.isAdmin()));
//                users.sort((o1, o2) -> {
//                    boolean b1 = o1.isAdmin();// TODO this does nothing. Think of it
//                    boolean b2 = o2.isAdmin();
//                    return (b1 ^ b2) ? (b1 ? 1 : -1) : 0;
//                });
                break;
            case "banned":
                users.sort((User u1, User u2) -> u1.isBanned().compareTo(u2.isBanned()));
//                users.sort((o1, o2) -> {
//                    boolean b1 = o1.isBanned();// TODO this does nothing. Think of it
//                    boolean b2 = o2.isBanned();
//                    return (b1 ^ b2) ? (b1 ? 1 : -1) : 0;
//                });
                break;
            default:
                break;
        }

        Set<User> sorted = new LinkedHashSet<>();
        sorted.addAll(users);

        req.getSession().setAttribute("sortedUsers", sorted);
        resp.sendRedirect(fromULR);
    }
}
