package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
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
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dsharko on 8/8/2016.
 */
public class TopRatedServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MovieService movieService = new MovieService();
        List<Movie> movies = null;
        try {
            movies = movieService.getAllMovies();
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing user ID", e);
            return;
        }

        if (movies == null) {
            req.setAttribute("movies", movies);
            req.getRequestDispatcher("/resources/views/home.jsp").forward(req, resp);
            return;
        }

        movies.sort((Movie m1, Movie m2) -> m2.getRating().compareTo(m1.getRating()));
        movies = movies.subList(0,10);

        req.setAttribute("movies", movies);
        req.getRequestDispatcher("/resources/views/home.jsp").forward(req, resp);
    }
}
