package com.moviesApp.controller;

import com.moviesApp.UrlParametersManager;
import com.moviesApp.entities.Movie;
import com.moviesApp.entities.Review;
import com.moviesApp.entities.User;
import com.moviesApp.service.MovieService;
import com.moviesApp.service.ReviewService;
import com.moviesApp.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dsharko on 8/4/2016.
 */
public class MovieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Long[] movieID = new Long[1];

        UrlParametersManager.getUrlParams(req.getQueryString()).forEach((key, value) -> {
            if (key.equals("movieId")) {// TODO think if multiply params are necessary
                movieID[0] = Long.valueOf(value.get(0));
            }
        });

        MovieService movieService = new MovieService();
        Movie movie = null;
        ReviewService reviewService = new ReviewService();
        List<Review> reviews = reviewService.getReviewsByMovieId(movieID[0]);
        UserService userService = new UserService();

        Map<Long, String> users = new HashMap<Long, String>();
        reviews.forEach(review -> {
            users.put(review.getUserId(), userService.getUserByID(review.getUserId()).getName());
        });

        if (movieID[0] != null) {
            movie = movieService.getMovieByID(movieID[0]);
        }

        if (movie == null) {
            req.setAttribute("errorDetails", "Something wrong has happened and movie can't be found :(");
            resp.sendRedirect(req.getContextPath() + "/error");
        } else {
            req.setAttribute("movie", movie);
            req.setAttribute("users", users);
            req.setAttribute("reviews", reviews);
            req.getRequestDispatcher("/resources/views/movie.jsp").forward(req, resp);

        }

    }
}
