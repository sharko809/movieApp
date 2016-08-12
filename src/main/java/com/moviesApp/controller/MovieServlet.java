package com.moviesApp.controller;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dsharko on 8/4/2016.
 */
public class MovieServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Long[] movieID = new Long[1];

        UrlParametersManager.getUrlParams(req.getQueryString()).forEach((key, value) -> {
            if (key.equals("movieId")) {// TODO think if multiply params are necessary
                movieID[0] = Long.valueOf(value.get(0));// TODO why 0 is hardcoded. Make it OK
            }
        });

        MovieService movieService = new MovieService();
        Movie movie = null;
        ReviewService reviewService = new ReviewService();
        List<Review> reviews = new ArrayList<>();
        if (movieID[0] != null) {
            if (movieID[0] >= 1) {
                try {
                    reviews = reviewService.getReviewsByMovieId(movieID[0]);
                } catch (SQLException e) {
                    e.printStackTrace();
                    req.getSession().setAttribute("errorDetails", e);
                    resp.sendRedirect(req.getContextPath() + "/error");
                    return;
                }
            }
        }
        UserService userService = new UserService();

        Map<Long, String> users = new HashMap<Long, String>();
        if (reviews.size() >= 1) {
            for (Review review : reviews) {
                User user = null;
                try {
                    user = userService.getUserByID(review.getUserId());
                } catch (SQLException e) {
                    e.printStackTrace();
                    req.getSession().setAttribute("errorDetails", e);
                    resp.sendRedirect(req.getContextPath() + "/error");
                    return;
                }
                if (user != null) {
                    users.put(review.getUserId(), user.getName());
                } else {
                    LOGGER.error("No user with ID " + review.getUserId() + " found for review ID " + review.getId() + " movie ID " + review.getMovieId());
                }
            }
        }

        if (movieID[0] != null) {
            try {
                movie = movieService.getMovieByID(movieID[0]);
            } catch (SQLException e) {
                e.printStackTrace();
                req.getSession().setAttribute("errorDetails", e);
                resp.sendRedirect(req.getContextPath() + "/error");
                return;
            }
        }

        if (movie == null) {
            req.getSession().setAttribute("errorDetails", "Something wrong has happened and movie can't be found :(");
            resp.sendRedirect(req.getContextPath() + "/error");
        } else {
            req.setAttribute("movie", movie);
            req.setAttribute("users", users);
            req.setAttribute("reviews", reviews);
            req.getRequestDispatcher("/resources/views/movie.jsp").forward(req, resp);

        }

    }
}
