package com.moviesApp.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dsharko on 8/1/2016.
 */
public class AuthorizedAccessFilter implements Filter {

    private FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (filterConfig == null) {
            return;
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        System.out.println("double login check!");
        if (session.getAttribute("user") == null) {
            System.out.println("filter OK!");
            filterChain.doFilter(request, response);
        } else {
            System.out.println("filter NOT OK!");
            request.getSession().setAttribute("errorDetails", "You are already authorized");
//            request.getRequestDispatcher("ErrorServlet").forward(request, response);
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }

    public void destroy() {
        this.filterConfig = null;
    }
}
