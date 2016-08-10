package com.moviesApp.controller;

import com.moviesApp.entities.User;
import com.moviesApp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
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
        List<User> users = userService.getAllUsers();
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
                users.sort((User u1, User u2) -> u1.getAdmin().compareTo(u2.getAdmin()));
//                users.sort((o1, o2) -> {
//                    boolean b1 = o1.getAdmin();// TODO this does nothing. Think of it
//                    boolean b2 = o2.getAdmin();
//                    return (b1 ^ b2) ? (b1 ? 1 : -1) : 0;
//                });
                break;
            case "banned":
                users.sort((User u1, User u2) -> u1.getBanned().compareTo(u2.getBanned()));
//                users.sort((o1, o2) -> {
//                    boolean b1 = o1.getBanned();
//                    boolean b2 = o2.getBanned();
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
