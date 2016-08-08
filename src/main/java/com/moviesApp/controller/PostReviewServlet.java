package com.moviesApp.controller;

import com.moviesApp.UrlParametersManager;
import com.moviesApp.entities.Movie;
import com.moviesApp.entities.Review;
import com.moviesApp.entities.User;
import com.moviesApp.service.MovieService;
import com.moviesApp.service.ReviewService;
import com.moviesApp.validation.ReviewValidator;
import com.moviesApp.validation.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 8/5/2016.
 */
public class PostReviewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = ((User) req.getSession().getAttribute("user")).getId();
        Long movieId = Long.valueOf(req.getParameter("movieID"));
        String reviewTitle = req.getParameter("reviewTitle");
        Integer userRating = Integer.valueOf(req.getParameter("userRating"));
        String reviewText = req.getParameter("reviewText");
        Date postDate = new Date(new java.util.Date().getTime());

        Review review = new Review();
        review.setUserId(userId);
        review.setMovieId(movieId);
        review.setTitle(reviewTitle);
        review.setRating(userRating);
        review.setReviewText(reviewText);
        review.setPostDate(postDate);

        // TODO validate!!!
        Validator validator = new ReviewValidator();
        List<String> errors = validator.validate(review);

//        String from = UrlParametersManager.makeURL(req.getParameter("from"), req.getParameter("query"));
        String from = "/home";// TODO think of it. But I prefer let it be default.
        String redirect = req.getParameter("redirectFrom");
        if (redirect != null) {
            if (!redirect.isEmpty()) {
                from = redirect;
            }
        }

        if (errors.isEmpty()) {
            ReviewService reviewService = new ReviewService();
            reviewService.createReview(userId, movieId, postDate, reviewTitle, userRating, reviewText);
            updateMovieRating(review.getMovieId(), review.getRating());
            req.getSession().removeAttribute("reviewError");
            resp.sendRedirect(from);
        } else {
//            req.getSession().setAttribute("reviewError", errors);
            req.getSession().setAttribute("reviewError", errors);
//            req.getRequestDispatcher("/movie.jsp").forward(req, resp);
            resp.sendRedirect(from);
        }
    }

    private void updateMovieRating(Long movieID, Integer rating) {
        MovieService movieService = new MovieService();
        Movie movieToUpdate = movieService.getMovieByID(movieID);

        if (movieToUpdate != null) {
            Double oldRating = movieToUpdate.getRating();
            Double newRating;
            if (oldRating == 0.0) {
                newRating = Double.valueOf(rating);
            } else {
                newRating = (oldRating + rating) / 2;
            }
            movieToUpdate.setRating(newRating);
            movieService.updateMovie(movieToUpdate);
        }

    }
}
