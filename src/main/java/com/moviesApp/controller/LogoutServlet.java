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
        HttpSession session = req.getSession();// TODO not sure if it can be null
        if (session != null) {
            session.removeAttribute("user");// TODO add "false" to getSession()?
            session.invalidate();
            System.out.println("logout OK");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doPost(req, resp);// TODO I'm TOTALLY NOT sure about this
//    }
}
