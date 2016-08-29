package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.UrlParametersManager;
import com.moviesApp.entities.Movie;
import com.moviesApp.entities.Review;
import com.moviesApp.entities.User;
import com.moviesApp.service.MovieService;
import com.moviesApp.service.ReviewService;
import com.moviesApp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MovieServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long movieID = 0L;

        Map<String, List<String>> urlParams = UrlParametersManager.getUrlParams(req.getQueryString());

        if (urlParams != null) {
            Optional<List<String>> value = urlParams.entrySet().stream()
                    .filter(params -> "movieId".equals(params.getKey()))
                    .map(Map.Entry::getValue)
                    .findFirst();
            if (value.isPresent()) {
                if (!value.get().isEmpty() && value.get().size() == 1) {
                    try {
                        movieID = Long.parseLong(value.get().get(0));
                    } catch (NumberFormatException e) {
                        ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing query", e);
                        return;
                    }
                }
            }
        } else {
            req.setAttribute("errorDetails", "Invalid request url");
            req.getRequestDispatcher("/error").forward(req, resp);
        }

        if (movieID < 1) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Wrong movie id", new IllegalArgumentException("Wrong movie id"));
        }

        MovieService movieService = new MovieService();
        Movie movie;
        ReviewService reviewService = new ReviewService();
        List<Review> reviews;
        try {
            reviews = reviewService.getReviewsByMovieId(movieID);
            reviews.sort((r1, r2) -> r2.getPostDate().compareTo(r1.getPostDate()));
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }

        UserService userService = new UserService();
        Map<Long, String> users = new HashMap<Long, String>();
        if (reviews.size() >= 1) {
            for (Review review : reviews) {
                if (review.getUserId() == null) {
                    ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Null user id", new NullPointerException("Null user id"));
                } else if (review.getUserId() < 1) {
                    ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Wrong user id", new IllegalArgumentException("Wrong user id"));
                }
                User user;
                try {
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

        try {
            movie = movieService.getMovieByID(movieID);
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }

        if (movie == null) {
            req.setAttribute("errorDetails", "Something wrong has happened and movie can't be found :(");
            req.getRequestDispatcher("/error").forward(req, resp);
        } else {
            req.setAttribute("movie", movie);
            req.setAttribute("users", users);
            req.setAttribute("reviews", reviews);
            req.getRequestDispatcher("/resources/views/movie.jsp").forward(req, resp);

        }

    }
}
