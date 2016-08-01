package com.moviesApp.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 8/1/2016.
 */
public class ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> errorDetails = new ArrayList<String>();
        errorDetails.add("Server name: " + req.getServerName());
        errorDetails.add("Server port: " + req.getServerPort());
        errorDetails.add("Requested URL: " + req.getRequestURL());
        errorDetails.add("Reason: " + req.getSession().getAttribute("errorDetails"));
        req.setAttribute("errorDetails", errorDetails);
        System.out.println("error servlet");
        req.getRequestDispatcher("/error.jsp").forward(req, resp);
    }
}
