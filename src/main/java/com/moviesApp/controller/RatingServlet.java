package com.moviesApp.controller;

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
import java.util.List;

/**
 * Created by dsharko on 8/9/2016.
 */
public class RatingServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long movieID = Long.valueOf(req.getParameter("movieID"));
        String from = req.getParameter("redirectFrom");

        MovieService movieService = new MovieService();
        Movie movie = null;
        try {
            movie = movieService.getMovieByID(movieID);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("errorDetails", e);
            req.getRequestDispatcher("/error").forward(req, resp);
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
            e.printStackTrace();
            req.setAttribute("errorDetails", e);
            req.getRequestDispatcher("/error").forward(req, resp);
            return;
        }
        if (reviews == null) {
            LOGGER.error("NO reviews found. So rating can't be updated.");// TODO mb inform user?
            return;
        }

        Double totalRating = 0D;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        Double newRating = totalRating / reviews.size();
        movie.setRating(newRating);

        try {
            movieService.updateMovie(movie);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("errorDetails", e);
            req.getRequestDispatcher("/error").forward(req, resp);
            return;
        }

        resp.sendRedirect(from);


    }
}
