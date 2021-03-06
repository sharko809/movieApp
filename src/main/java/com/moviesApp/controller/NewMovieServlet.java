package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.entities.Movie;
import com.moviesApp.service.MovieService;
import com.moviesApp.validation.MovieValidator;
import com.moviesApp.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class NewMovieServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("result", null);
        req.getRequestDispatcher("/resources/views/addmovie.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String title = req.getParameter("title");
        String director = req.getParameter("director");
        String releaseDate = req.getParameter("releaseDate");
        String posterUrl = req.getParameter("posterUrl");
        String trailerUrl = req.getParameter("trailerUrl");
        String description = req.getParameter("description");

        Movie movie = new Movie();
        movie.setMovieName(title);
        movie.setDirector(director);
        if (releaseDate.isEmpty()) {
            movie.setReleaseDate(new Date(new java.util.Date().getTime()));
        } else {
            try {
                movie.setReleaseDate(Date.valueOf(releaseDate));
            } catch (IllegalArgumentException e) {
                ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing date", e);
                return;
            }
        }
        movie.setPosterURL(posterUrl);
        movie.setTrailerURL(trailerUrl);
        movie.setRating(0D);
        movie.setDescription(description);

        Validator validator = new MovieValidator();
        List<String> errors = validator.validate(movie);

        if (errors.isEmpty()) {
            MovieService movieService = new MovieService();
            try {
                movieService.addMovie(movie.getMovieName(), movie.getDirector(), movie.getReleaseDate(), movie.getPosterURL(), movie.getTrailerURL(), 0D, movie.getDescription());
            } catch (SQLException e) {
                ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
                return;
            }
            req.setAttribute("result", "Movie " + title + " added successfully.");
            resp.sendRedirect("/admin/addmovie");
        } else {
            req.setAttribute("result", errors);
            req.setAttribute("movie", movie);
            req.getRequestDispatcher("/resources/views/addmovie.jsp").forward(req, resp);
        }


    }


}
