package com.moviesApp.controller;

import com.moviesApp.UrlParametersManager;
import com.moviesApp.entities.Movie;
import com.moviesApp.entities.Review;
import com.moviesApp.entities.User;
import com.moviesApp.service.MovieService;
import com.moviesApp.service.ReviewService;
import com.moviesApp.validation.ReviewValidator;
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
import java.util.List;

/**
 * Created by dsharko on 8/5/2016.
 */
public class PostReviewServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

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

        Validator validator = new ReviewValidator();
        List<String> errors = validator.validate(review);

        String from = "/home";
        String redirect = req.getParameter("redirectFrom");
        if (redirect != null) {
            if (!redirect.isEmpty()) {
                from = redirect;
            }
        }

        if (errors.isEmpty()) {
            ReviewService reviewService = new ReviewService();
            try {
                reviewService.createReview(userId, movieId, postDate, reviewTitle, userRating, reviewText);
            } catch (SQLException e) {
                e.printStackTrace();
                req.getSession().setAttribute("errorDetails", e);
                resp.sendRedirect(req.getContextPath() + "/error");
                return;
            }
            try {
                updateMovieRating(review.getMovieId(), review.getRating());
            } catch (SQLException e) {
                e.printStackTrace();
                req.getSession().setAttribute("errorDetails", e);
                resp.sendRedirect(req.getContextPath() + "/error");
                return;
            }
            req.getSession().removeAttribute("reviewError");
            req.getSession().removeAttribute("review");
            resp.sendRedirect(from);
        } else {
            req.getSession().setAttribute("reviewError", errors);
            req.getSession().setAttribute("review" ,review);
            resp.sendRedirect(from);
        }
    }

    private void updateMovieRating(Long movieID, Integer rating) throws SQLException {
        MovieService movieService = new MovieService();
        Movie movieToUpdate = movieService.getMovieByID(movieID);

        ReviewService reviewService = new ReviewService();
        List<Review> reviews = reviewService.getReviewsByMovieId(movieID);

        if (reviews == null) {
            LOGGER.error("Unable to update rating");
            return;// TODO this might be totally unnecessary since 'reviews' (I think so) is never null
        }

        if (!reviews.isEmpty()) {
            if (movieToUpdate != null) {
                Double totalRating = 0D;
                for (Review review : reviews) {
                    totalRating += review.getRating();
                }
                Double newRating = (totalRating + rating) / (reviews.size() + 1);
                movieToUpdate.setRating(newRating);
                movieService.updateMovie(movieToUpdate);
            }
        } else {
            movieToUpdate.setRating(Double.valueOf(rating));
            movieService.updateMovie(movieToUpdate);
        }



    }
}
