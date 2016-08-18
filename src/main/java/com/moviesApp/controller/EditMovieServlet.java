package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.entities.Movie;
import com.moviesApp.entities.Review;
import com.moviesApp.entities.User;
import com.moviesApp.service.MovieService;
import com.moviesApp.service.ReviewService;
import com.moviesApp.service.UserService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if (movieId < 1) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Wrong movie id", new IllegalArgumentException("Wrong movie id"));
        }

        MovieService movieService = new MovieService();
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
        ReviewService reviewService = new ReviewService();
        List<Review> reviews = new ArrayList<>();

        try {
            reviews = reviewService.getReviewsByMovieId(movieId);
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }

        Map<Long, String> users = new HashMap<Long, String>();
        if (reviews.size() >= 1) {
            for (Review review : reviews) {
                if (review.getUserId() == null) {
                    ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Null user id", new NullPointerException("Null user id"));
                } else if (review.getUserId() < 1) {
                    ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Null user id", new IllegalArgumentException("Wrong user id"));
                }
                User user = null;
                try {
                    UserService userService = new UserService();
                    user = userService.getUserByID(review.getUserId());
                } catch (SQLException e) {
                    ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
                    return;
                }
                if (user != null) {
                    users.put(review.getUserId(), user.getName());
                } else {
                    LOGGER.error("No user with ID " + review.getUserId() + " found for review ID " + review.getId() + " movie ID " + review.getMovieId());
                }
            }
        }
        req.setAttribute("movie", movie);
        req.setAttribute("users", users);
        req.setAttribute("reviews", reviews);
        req.getRequestDispatcher("/resources/views/editmovie.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String director = req.getParameter("director");
        String releaseDate = req.getParameter("releaseDate");
        String posterUrl = req.getParameter("posterUrl");
        String trailerUrl = req.getParameter("trailerUrl");
        String description = req.getParameter("description");
        Long movieID = 0L;
        try {
            movieID = Long.valueOf(req.getParameter("movieID"));
        } catch (NumberFormatException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing movie ID", e);
            return;
        }

        if (movieID < 1) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Wrong movie id", new IllegalArgumentException("Wrong movie id"));
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
            return;
        }

        movie.setMovieName(title);
        movie.setDirector(director);
        if (releaseDate.isEmpty()) {
            movie.setReleaseDate(movie.getReleaseDate());
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
            req.setAttribute("result", "Movie updated");
            req.setAttribute("updMovie", movie);
            resp.sendRedirect("/admin/editmovie?movieID=" + movie.getId());
        } else {
            req.setAttribute("result", errors);
            req.setAttribute("updMovie", movie);
            req.getRequestDispatcher("/resources/views/editmovie.jsp").forward(req, resp);
        }


    }
}
