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
import java.util.List;
import java.util.Map;
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
        Map<String, List<String>> urlParams = UrlParametersManager.getUrlParams(request.getQueryString());

        if (urlParams != null) {
            urlParams.forEach((key, value) -> {
                if ("id".equals(key)) {
                    userID[0] = Long.valueOf(value.get(0));
                }
            });
        } else {
            request.getSession().setAttribute("errorDetails", "Invalid request url");
            response.sendRedirect(request.getContextPath() + "/error");
            return;
        }

        if (Objects.equals(currentUser.getId(), userID[0])) {
            filterChain.doFilter(request, response);// TODO can I pass userID from here? I think - yes.
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
