package com.moviesApp.filter;

import com.moviesApp.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UnauthorizedAccessFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();
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

        if (session == null) {
            LOGGER.error("Not authorized access to: " + request.getRequestURI() + " Remote user details: " + request.getRemoteAddr());
            request.setAttribute("errorDetails", "You are not authorized");
            request.getRequestDispatcher("/error").forward(request, response);
        } else {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                LOGGER.error("Not authorized access to: " + request.getRequestURI() + " Remote user details: " + request.getRemoteAddr());
                request.setAttribute("errorDetails", "You are not authorized");
                request.getRequestDispatcher("/error").forward(request, response);
            } else {
                filterChain.doFilter(request, response);
            }
        }

    }

    public void destroy() {
        this.filterConfig = null;
    }
}
