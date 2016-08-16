package com.moviesApp.filter;

import com.moviesApp.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dsharko on 8/11/2016.
 */
public class SearchFilter implements Filter {

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

        if (request.getMethod().equals("GET")) {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                if (user.isAdmin()) {
                    request.getRequestDispatcher("/resources/views/admin.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/resources/views/home.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("/resources/views/home.jsp").forward(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }


    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
