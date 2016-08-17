package com.moviesApp.filter;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.UrlParametersManager;
import com.moviesApp.entities.User;
import com.sun.xml.internal.rngom.digested.DDataPattern;
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
import java.util.Optional;

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
        Long userID = 0L;
        Map<String, List<String>> urlParams = UrlParametersManager.getUrlParams(request.getQueryString());

        if (urlParams != null) {
            Optional<List<String>> value = urlParams.entrySet().stream()
                    .filter(params -> "id".equals(params.getKey()))
                    .map(Map.Entry::getValue)
                    .findFirst();

            if (value.isPresent()) {
                if (!value.get().isEmpty() && value.get().size() == 1){
                    try {
                        userID = Long.parseLong(value.get().get(0));
                    } catch (NumberFormatException e) {
                        ExceptionsUtil.sendException(LOGGER, request, response, "/error", "Invalid request URL", e);
                    }
                }
            }

        } else {
            request.setAttribute("errorDetails", "Invalid request URL");
            request.getRequestDispatcher("/error").forward(request, response);
        }

        if (Objects.equals(currentUser.getId(), userID)) {
            filterChain.doFilter(request, response);// TODO can I pass userID from here? I think - yes.
        } else {
            LOGGER.error("Attempt to access another users account. User: " +
                    ((User) session.getAttribute("user")).getLogin() + " admin: " + ((User) session.getAttribute("user")).isAdmin());
            request.setAttribute("errorDetails", "You do not have permission to access this page");
            request.getRequestDispatcher("/error").forward(request, response);
        }


    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
