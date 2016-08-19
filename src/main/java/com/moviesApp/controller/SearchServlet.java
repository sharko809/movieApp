package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.UrlParametersManager;
import com.moviesApp.entities.Movie;
import com.moviesApp.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by dsharko on 8/8/2016.
 */
public class SearchServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, List<String>> urlParams = UrlParametersManager.getUrlParams(req.getQueryString());
        Optional<List<String>> value = urlParams.entrySet().stream()
                            .filter(params -> "searchInput".equals(params.getKey()))
                            .map(Map.Entry::getValue)
                            .findFirst();
        String search = "";
        if (value.isPresent()) {
            search = value.get().get(0);
        }

        MovieService movieService = new MovieService();
        List<Movie> result = new ArrayList<>();
        List<Movie> movies;
        try {
            movies = movieService.getAllMovies();
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }

        if (!movies.isEmpty()) {
            String finalSearch = search;
            movies.stream()
                    .filter(m -> m.getMovieName().toLowerCase().contains(finalSearch.toLowerCase()))
                    .forEach(result::add);
        }

        req.setAttribute("movies", result);
        req.setAttribute("searchRequest", search);
        req.getRequestDispatcher("/resources/views/searchresult.jsp").forward(req, resp);
    }
}
