package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.entities.Movie;
import com.moviesApp.entities.Review;
import com.moviesApp.service.MovieService;
import com.moviesApp.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by dsharko on 8/9/2016.
 */
public class RatingServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long movieID = 0L;
        try {
            movieID = Long.valueOf(req.getParameter("movieID"));
        } catch (NumberFormatException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing movie ID", e);
            return;
        }
        String from = req.getParameter("redirectFrom");

        MovieService movieService = new MovieService();
        Movie movie = null;
        try {
            movie = movieService.getMovieByID(movieID);
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }
        if (movie == null) {
            LOGGER.error("NO movie found. So rating can't be updated.");// TODO mb inform user?
            return;
        }

        ReviewService reviewService = new ReviewService();
        List<Review> reviews = null;
        try {
            reviews = reviewService.getReviewsByMovieId(movieID);
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }
        if (reviews == null || reviews.isEmpty()) {
            LOGGER.error("NO reviews found. So rating can't be updated.");// TODO mb inform user?
            resp.sendRedirect(from);
            return;
        }

        Double totalRating = 0D;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        Double newRating = totalRating / reviews.size();
        DecimalFormat df = new DecimalFormat("#.##");
        try {
            newRating = Double.valueOf(df.format(newRating));
        } catch (NumberFormatException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }
        movie.setRating(newRating);

        try {
            movieService.updateMovie(movie);
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }

        resp.sendRedirect(from);


    }
}
