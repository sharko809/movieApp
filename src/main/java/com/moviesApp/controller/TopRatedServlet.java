package com.moviesApp.controller;

import com.moviesApp.entities.Movie;
import com.moviesApp.service.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dsharko on 8/8/2016.
 */
public class TopRatedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MovieService movieService = new MovieService();
        List<Movie> movies = null;
        try {
            movies = movieService.getAllMovies();
        } catch (SQLException e) {
            e.printStackTrace();
            req.getSession().setAttribute("errorDetails", e);
            resp.sendRedirect(req.getContextPath() + "/error");
            return;
        }

        if (movies == null) {
            req.setAttribute("movies", movies);
            req.getRequestDispatcher("/resources/views/home.jsp").forward(req, resp);
            return;
        }

        movies.sort((Movie m1, Movie m2) -> m2.getRating().compareTo(m1.getRating()));

        Set<Movie> sorted = new LinkedHashSet<>();
        sorted.addAll(movies);

        req.setAttribute("movies", sorted);
        req.getRequestDispatcher("/resources/views/home.jsp").forward(req, resp);
    }
}
