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
import java.util.List;

/**
 * Created by dsharko on 8/10/2016.
 */
public class UsersServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

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
        PagedEntity pagedUsers = null;
        List<User> users = null;
        int numberOfRecords = 1;
        String sortBy = (String) req.getSession().getAttribute("sortBy");

        if (sortBy != null) {
            if ("id".equals(sortBy)) {
                req.getSession().removeAttribute("sortBy");
            }
            try {
                pagedUsers = userService.getUsersSorted((page-1)*recordsPegPage, recordsPegPage, sortBy);
            } catch (SQLException e) {
                ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
                return;
            }
        } else {
            try {
                pagedUsers = userService.getAllUsersLimit((page-1)*recordsPegPage, recordsPegPage);
            } catch (SQLException e) {
                ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
                return;
            }
        }

        if (pagedUsers != null) {
            users = (List<User>) pagedUsers.getEntity();
            numberOfRecords = pagedUsers.getNumberOfRecords();
        }
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPegPage);

        req.setAttribute("users", users);
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("currentPage", page);
        req.getRequestDispatcher("/resources/views/users.jsp").forward(req, resp);
    }

}
