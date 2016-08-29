package com.moviesApp.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> errorDetails = new ArrayList<String>();
        Object errorAttribute = req.getAttribute("errorDetails");
        if (errorAttribute instanceof String) {
            String errors = (String) errorAttribute;
            errorDetails.add("Server name: " + req.getServerName());
            errorDetails.add("Server port: " + req.getServerPort());
            errorDetails.add("Requested URL: " + req.getRequestURL());
            errorDetails.add("Reason: " + errors);
            req.setAttribute("errorDetails", errorDetails);
            req.getRequestDispatcher("/resources/views/error.jsp").forward(req, resp);
        } else if (errorAttribute instanceof Throwable) {
            Throwable errors = ((Throwable) errorAttribute).getCause();
            errorDetails.add("Server name: " + req.getServerName());
            errorDetails.add("Server port: " + req.getServerPort());
            errorDetails.add("Requested URL: " + req.getRequestURL());
            errorDetails.add("Reason: " + errors);
            req.setAttribute("errorDetails", errorDetails);
            req.getRequestDispatcher("/resources/views/error.jsp").forward(req, resp);
        } else {
            errorDetails.add("Server name: " + req.getServerName());
            errorDetails.add("Server port: " + req.getServerPort());
            errorDetails.add("Requested URL: " + req.getRequestURL());
            errorDetails.add("Reason: " + "Unexpected error");
            req.setAttribute("errorDetails", errorDetails);
            req.getRequestDispatcher("/resources/views/error.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
