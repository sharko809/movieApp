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
        String reqUrl = (String) req.getAttribute("reqUrl");
        if (errorAttribute instanceof String) {
            String errors = (String) errorAttribute;
            if (reqUrl != null) {
                errorDetails.add("Requested address: " + reqUrl);
            }
            errorDetails.add("Reason: " + errors);
            req.setAttribute("errorDetails", errorDetails);
            req.getRequestDispatcher("/resources/views/error.jsp").forward(req, resp);
        } else if (errorAttribute instanceof Throwable) {
            Throwable errors = ((Throwable) errorAttribute).getCause();
            if (reqUrl != null) {
                errorDetails.add("Requested address: " + reqUrl);
            }
            errorDetails.add("Reason: " + errors);
            req.setAttribute("errorDetails", errorDetails);
            req.getRequestDispatcher("/resources/views/error.jsp").forward(req, resp);
        } else {
            if (reqUrl != null) {
                errorDetails.add("Requested address: " + reqUrl);
            }
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
