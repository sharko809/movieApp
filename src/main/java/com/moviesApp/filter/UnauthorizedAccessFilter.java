package com.moviesApp.filter;

import com.moviesApp.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            request.getSession().setAttribute("errorDetails", "You are not authorized");
            response.sendRedirect(request.getContextPath() + "/error");
        } else {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                LOGGER.error("Not authorized access to: " + request.getRequestURI() + " Remote user details: " + request.getRemoteAddr());
                request.getSession().setAttribute("errorDetails", "You are not authorized");
                response.sendRedirect(request.getContextPath() + "/error");
            } else {
                filterChain.doFilter(request, response);
            }
        }


//        if (session.getAttribute("user") == null) {
//            LOGGER.warn("Attempt to get unauthorized access to content");
//            request.getSession().setAttribute("errorDetails", "Not authorized user");
//            response.sendRedirect(request.getContextPath() + "/error");
//        } else {
//            filterChain.doFilter(request, response);
//        }

    }

    public void destroy() {
        this.filterConfig = null;
    }
}
