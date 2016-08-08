package com.moviesApp.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dsharko on 8/1/2016.
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);// TODO not sure if it can be null
        if (session != null) {
            session.removeAttribute("user");// TODO add "false" to getSession()?
            session.invalidate();
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("errorDetails", "You are not logged in and therefore can't logout");
            resp.sendRedirect(req.getContextPath() + "/error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("errorDetails", "Attempt to logout in unusual way. Please logout using correspondent button.");
        resp.sendRedirect(req.getContextPath() + "/error");
    }
}
