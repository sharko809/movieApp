package com.moviesApp.filter;

import com.moviesApp.UrlParametersManager;
import com.moviesApp.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by dsharko on 8/10/2016.
 */
public class AccountFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();
    private FilterConfig filterConfig;

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

        User currentUser = (User) session.getAttribute("user");
        final Long[] userID = new Long[1];
        UrlParametersManager.getUrlParams(request.getQueryString()).forEach((key, value) -> {
            if (key.equals("id")) {
                userID[0] = Long.valueOf(value.get(0));
            }
        });


        if (Objects.equals(userID[0], currentUser.getId())) {
            filterChain.doFilter(request, response);
        } else {
            LOGGER.error("Attempt to access another users account. User: " +
                    ((User) session.getAttribute("user")).getLogin() + " admin: " + ((User) session.getAttribute("user")).isAdmin());
            request.getSession().setAttribute("errorDetails", "You do not have permission to access this page");
            response.sendRedirect(request.getContextPath() + "/error");
        }


    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
