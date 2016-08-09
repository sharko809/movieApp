package com.moviesApp.controller;

import com.moviesApp.entities.Movie;
import com.moviesApp.entities.Review;
import com.moviesApp.service.MovieService;
import com.moviesApp.service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by dsharko on 8/9/2016.
 */
public class RatingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long movieID = Long.valueOf(req.getParameter("movieID"));
        String from = req.getParameter("redirectFrom");

        MovieService movieService = new MovieService();
        Movie movie = movieService.getMovieByID(movieID);

        ReviewService reviewService = new ReviewService();
        List<Review> reviews = reviewService.getReviewsByMovieId(movieID);
        Double totalRating = 0D;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        Double newRating = totalRating / reviews.size();
        movie.setRating(newRating);

        movieService.updateMovie(movie);

        resp.sendRedirect(from);


    }
}
