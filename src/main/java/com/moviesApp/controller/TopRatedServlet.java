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
import java.util.List;

public class TopRatedServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MovieService movieService = new MovieService();
        List<Movie> movies;
        try {
            movies = movieService.getAllMovies();
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing user ID", e);
            return;
        }

        if (movies != null) {
            movies.sort((m1, m2) -> m2.getRating().compareTo(m1.getRating()));
            if (movies.size() >= 10) {
                movies = movies.subList(0, 10);
            }
        }

        req.setAttribute("movies", movies);
        req.getRequestDispatcher("/resources/views/toprated.jsp").forward(req, resp);
    }
}
