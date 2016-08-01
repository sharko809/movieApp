package com.moviesApp.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by dsharko on 8/1/2016.
 */
public class UnauthorizedAccessFilter implements Filter {

    private FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if (filterConfig == null) {
            return;
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        String loginURI = request.getContextPath() + "/home";
        String errorURI = request.getContextPath() + "/error";
        System.out.println(request.getContextPath());

        boolean loginRequest = request.getRequestURI().equals(loginURI);

        System.out.println(session.getAttribute("user") + " " + loginRequest);

        if (session.getAttribute("user") == null) {
            System.out.println("go to error " + errorURI);
            request.getSession().setAttribute("errorDetails", "Not authorized user");
//            request.getRequestDispatcher("ErrorServlet").forward(request, response);
            response.sendRedirect(request.getContextPath() + "/error");
        } else {
            System.out.println("filter OK!");
            filterChain.doFilter(request, response);
        }

        System.out.println("called filter");
//        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
        this.filterConfig = null;
    }
}
