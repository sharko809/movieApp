package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.entities.PagedEntity;
import com.moviesApp.entities.User;
import com.moviesApp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by dsharko on 8/10/2016.
 */
public class UsersServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        UserService userService = new UserService();
//        List<User> users = new ArrayList<>();
//        try {
//            users = userService.getAllUsers();
//        } catch (SQLException e) {
//            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
//            return;
//        }
//        // TODO add pagination!!!
//        Object sortedAttribute = req.getSession().getAttribute("sortedUsers");
//        req.getSession().removeAttribute("sortedUsers");
//
//        if (sortedAttribute instanceof Set) {
//            Set<User> sortedUsers = (Set<User>) sortedAttribute;
//            req.getSession().setAttribute("users", sortedUsers);
//            req.getRequestDispatcher("/resources/views/users.jsp").forward(req, resp);
//            return;
//        }
//
//        req.setAttribute("users", users);
//        req.getRequestDispatcher("/resources/views/users.jsp").forward(req, resp);
//    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");
        int page = 1;
        int recordsPegPage = 10;
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page <= 0) {
                    resp.sendRedirect("users?page=1");
                    return;
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect("users?page=1");
                return;
            }
        }

        UserService userService = new UserService();
        PagedEntity pagedUsers;
        try {
            pagedUsers = userService.getAllUsersLimit((page-1)*recordsPegPage, recordsPegPage);
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }
        List<User> users = null;
        int numberOfRecords = 1;
        if (pagedUsers != null) {
            users = (List<User>) pagedUsers.getEntity();
            numberOfRecords = pagedUsers.getNumberOfRecords();
        }
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPegPage);

        Object sortedAttribute = req.getSession().getAttribute("sortedUsers");
        if (sortedAttribute != null) {
            req.getSession().removeAttribute("sortedUsers");
        }
        if (sortedAttribute instanceof Set) {
            Set<User> sortedUsers = (Set<User>) sortedAttribute;
            req.getSession().setAttribute("users", sortedUsers);
            req.setAttribute("numberOfPages", numberOfPages);
            req.setAttribute("currentPage", page);
            req.getRequestDispatcher("/resources/views/users.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("users", users);
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("currentPage", page);
        req.getRequestDispatcher("/resources/views/users.jsp").forward(req, resp);

    }
}
