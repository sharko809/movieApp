package com.moviesApp.filter;

import com.moviesApp.UrlParametersManager;
import com.moviesApp.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        String query = request.getQueryString();
        Map<String, List<String>> urlParams;

        if (query != null) {
            if (!query.isEmpty()) {
                urlParams = UrlParametersManager.getUrlParams(query);
                if (urlParams != null) {
                    Optional<List<String>> value = urlParams.entrySet().stream()
                            .filter(params -> "searchInput".equals(params.getKey()))
                            .map(Map.Entry::getValue)
                            .findFirst();

                    if (value.isPresent()) {
                        if (!value.get().isEmpty() && value.get().size() == 1) {
                            if (!value.get().get(0).isEmpty()) {
                                filterChain.doFilter(request, response);
                            } else {
                                request.setAttribute("movies", new ArrayList<>());
                                request.getRequestDispatcher("/resources/views/searchresult.jsp").forward(request, response);
                            }
                        } else {
                            request.setAttribute("movies", new ArrayList<>());
                            request.getRequestDispatcher("/resources/views/searchresult.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("movies", new ArrayList<>());
                        request.getRequestDispatcher("/resources/views/searchresult.jsp").forward(request, response);
                    }
                }
            } else {
                request.setAttribute("movies", new ArrayList<>());
                request.getRequestDispatcher("/resources/views/searchresult.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("movies", new ArrayList<>());
            request.getRequestDispatcher("/resources/views/searchresult.jsp").forward(request, response);
        }

    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
