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

/**
 * Created by dsharko on 8/10/2016.
 */
public class EditMovieServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String movieIdParam = req.getParameter("movieID");
        Long movieId = null;
        try {
            movieId = Long.valueOf(movieIdParam);
        } catch (NumberFormatException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing movie ID", e);
            return;
        }

        MovieService movieService = new MovieService();
        if (movieId >= 1) {
            Movie movie = null;
            try {
                movie = movieService.getMovieByID(movieId);
            } catch (SQLException e) {
                ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing user ID", e);
                return;
            }
            if (movie == null) {
                movie = new Movie();
            }
            req.setAttribute("movie", movie);
            req.getRequestDispatcher("/resources/views/editmovie.jsp").forward(req, resp);
        } else {
            req.setAttribute("errorDetails", "No movie found");
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String director = req.getParameter("director");
        String releaseDate = req.getParameter("releaseDate");
        String posterUrl = req.getParameter("posterUrl");
        String trailerUrl = req.getParameter("trailerUrl");
        String description = req.getParameter("description");
        Long movieID = null;
        try {
            movieID = Long.valueOf(req.getParameter("movieID"));
        } catch (NumberFormatException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing movie ID", e);
            return;
        }

        MovieService movieService = new MovieService();

        Movie movie = null;
        try {
            movie = movieService.getMovieByID(movieID);
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }
        if (movie == null) {
            req.setAttribute("errorDetails", "No movie found");
            req.getRequestDispatcher("/error").forward(req, resp);
        }

        movie.setMovieName(title);
        movie.setDirector(director);
        if (releaseDate.isEmpty()) {
            movie.setReleaseDate(new Date(new java.util.Date().getTime()));// TODO handle it in some other way
        } else {
            try {
                movie.setReleaseDate(Date.valueOf(releaseDate));// TODO check this
            } catch (IllegalArgumentException e) {
                ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing date", e);
                return;
            }
        }
        movie.setPosterURL(posterUrl);
        movie.setTrailerURL(trailerUrl);
        movie.setDescription(description);

        Validator validator = new MovieValidator();
        List<String> errors = validator.validate(movie);

        if (errors.isEmpty()) {
            try {
                movieService.updateMovie(movie);
            } catch (SQLException e) {
                ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
                return;
            }
            req.setAttribute("result", "Movie updated");// TODO ok, this is to be done properly. I need to properly display errors on the same page
            req.setAttribute("updMovie", movie);
            req.getRequestDispatcher("/resources/views/editmovie.jsp").forward(req, resp);
        } else {
            req.setAttribute("result", errors);
            req.setAttribute("updMovie", movie);
//            req.setAttribute("movie", movie);
            req.getRequestDispatcher("/resources/views/editmovie.jsp").forward(req, resp);
        }


    }
}
