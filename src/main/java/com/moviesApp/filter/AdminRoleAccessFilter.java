package com.moviesApp.filter;

import com.moviesApp.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dsharko on 8/3/2016.
 */
public class AdminRoleAccessFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();
    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (filterConfig == null) {
            return;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("user");

        if (user == null) {
            LOGGER.error("Not authorized access to: " + request.getRequestURI() + " Remote user details: " + request.getRemoteAddr());
            request.getSession().setAttribute("errorDetails", "You are not authorized");
            response.sendRedirect(request.getContextPath() + "/error");
        } else {
            if (user.isAdmin()) {
                filterChain.doFilter(request, response);
            } else {
                LOGGER.error("Attempt to access admin services without permission. User: " +
                        ((User) session.getAttribute("user")).getLogin() + " admin: " + ((User) session.getAttribute("user")).isAdmin());
                request.getSession().setAttribute("errorDetails", "You do not have permission to access this page");
                response.sendRedirect(request.getContextPath() + "/error");
            }
        }


    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
