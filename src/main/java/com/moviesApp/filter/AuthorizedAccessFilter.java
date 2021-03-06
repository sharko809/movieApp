package com.moviesApp.filter;

import com.moviesApp.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizedAccessFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();
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

        if (session == null) {
            request.setAttribute("errorDetails", "You are not authorized");
            LOGGER.error("Not authorized access to: " + request.getRequestURI() + " Remote user details: " + request.getRemoteAddr());
            request.getRequestDispatcher("/error").forward(request, response);
        } else {
            if (session.getAttribute("user") == null) {
                filterChain.doFilter(request, response);
            } else {
                User user = (User) session.getAttribute("user");
                request.setAttribute("errorDetails", "You are already authorized");
                LOGGER.warn("Attempt to access content for unauthorized users while authorized. User: " + user.getName() + " admin: " + user.isAdmin());
                request.getRequestDispatcher("/error").forward(request, response);
            }
        }

    }

    public void destroy() {
        this.filterConfig = null;
    }
}
