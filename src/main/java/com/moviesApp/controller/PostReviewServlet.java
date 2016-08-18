package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.entities.Movie;
import com.moviesApp.entities.Review;
import com.moviesApp.entities.User;
import com.moviesApp.service.MovieService;
import com.moviesApp.service.ReviewService;
import com.moviesApp.service.UserService;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dsharko on 8/5/2016.
 */
public class PostReviewServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = ((User) req.getSession().getAttribute("user")).getId();
        Long movieId = 0L;
        String reviewTitle = req.getParameter("reviewTitle");
        Integer userRating;
        try {
            movieId = Long.valueOf(req.getParameter("movieID"));
            userRating = Integer.valueOf(req.getParameter("userRating"));
        } catch (NumberFormatException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }
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
                updateMovieRating(review.getMovieId(), review.getRating());
            } catch (SQLException e ) {
                ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
                return;
            } catch (NumberFormatException e) {
                ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing rating", e);
                return;
            }
            resp.sendRedirect("/movies?movieId=" + movieId);
        } else {
            List<Review> reviews = null;
            Movie movie = null;
            Map<Long, String> users = null;
            try {
                reviews = getReviews(movieId);
                movie = getMovie(movieId);
                users = getUsers(reviews);
            } catch (SQLException e) {
                ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
                return;
            }
            req.setAttribute("reviews", reviews);
            req.setAttribute("movie", movie);
            req.setAttribute("users", users);
            req.setAttribute("reviewError", errors);
            req.setAttribute("review", review);
            req.getRequestDispatcher("/resources/views/movie.jsp").forward(req, resp);
        }
    }

    private void updateMovieRating(Long movieID, Integer rating) throws SQLException, NumberFormatException {
        MovieService movieService = new MovieService();
        Movie movieToUpdate = movieService.getMovieByID(movieID);

        ReviewService reviewService = new ReviewService();
        List<Review> reviews = reviewService.getReviewsByMovieId(movieID);

        if (reviews == null) {
            LOGGER.error("Unable to update rating");
            return;
        }

        if (!reviews.isEmpty()) {
            if (movieToUpdate != null) {
                Double totalRating = 0D;
                for (Review review : reviews) {
                    totalRating += review.getRating();
                }
                Double newRating = (totalRating + rating) / (reviews.size() + 1);
                DecimalFormat df = new DecimalFormat("#.##");
                try {
                    newRating = Double.valueOf(df.format(newRating));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Error parsing rating");
                }
                movieToUpdate.setRating(newRating);
                movieService.updateMovie(movieToUpdate);
            }
        } else {
            try {
                movieToUpdate.setRating(Double.valueOf(rating));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Error parsing rating");
            }
            movieService.updateMovie(movieToUpdate);
        }


    }

    private List<Review> getReviews(Long movieID) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        ReviewService reviewService = new ReviewService();
        if (movieID != null) {
            if (movieID >= 1) {
                reviews = reviewService.getReviewsByMovieId(movieID);
            }
        }
        return reviews;
    }

    private Movie getMovie(Long movieID) throws SQLException {
        Movie movie = null;
        if (movieID != null) {
            if (movieID >= 1) {
                MovieService movieService = new MovieService();
                movie = movieService.getMovieByID(movieID);
            }
        }
        return movie;
    }

    private Map<Long, String> getUsers(List<Review> reviews) throws SQLException {
        Map<Long, String> users = new HashMap<>();
        if (reviews.size() >= 1) {
            for (Review review : reviews) {
                User user = null;
                UserService userService = new UserService();
                user = userService.getUserByID(review.getUserId());
                if (user != null) {
                    users.put(review.getUserId(), user.getName());
                } else {
                    LOGGER.error("No user with ID " + review.getUserId() + " found for review ID " + review.getId() + " movie ID " + review.getMovieId());
                }
            }
        }
        return users;
    }

}
