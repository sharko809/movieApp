package com.moviesApp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 8/22/2016.
 */
public class UserSortServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();
    private final List<String> FIELDS;

    public UserSortServlet() {
        FIELDS = new ArrayList<>();
        FIELDS.add("id");
        FIELDS.add("login");
        FIELDS.add("username");
        FIELDS.add("isadmin");
        FIELDS.add("isbanned");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sortByParam = req.getParameter("sortBy");
        String sortBy;
        String from = req.getParameter("redirectFrom");
        if (from == null) {
            from = "users?page=1";
        }
        if (sortByParam != null) {
            if (FIELDS.stream().anyMatch(sortByParam::equals)) {
                sortBy = sortByParam;
                req.getSession().setAttribute("sortBy", sortBy);
            }
        }

        resp.sendRedirect(from);

    }
}
