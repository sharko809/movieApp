package com.moviesApp.service;

import com.moviesApp.daoLayer.ReviewDAO;
import com.moviesApp.entities.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by dsharko on 7/29/2016.
 */
public class ReviewService {

    private static final Logger LOGGER = LogManager.getLogger();

    public Long createReview(Long userID, Long movieID, Date postDate, String reviewTitle, Integer rating, String reviewText) {
        ReviewDAO reviewDAO = new ReviewDAO();
        Long reviewID = 0L;
        try {
            if (userID <= 0) {
                LOGGER.error("Failed to create new review. User id must be > 0. User id: " + userID);
                return 0L;
            }
            if (movieID <= 0) {
                LOGGER.error("Failed to create new review. Movie id must be > 0. Movie id: " + movieID);
                return 0L;
            }
            if (rating <= 0 || rating > 10) {
                LOGGER.error("Failed to create new review. Rating must be in [1-10] range. Rating: " + rating);
                return 0L;
            }
            if (reviewText == null || reviewText.trim().equals("") || reviewText.length() < 3) {
                LOGGER.error("Failed to create new review. Review must be not null and have at least 3 symbols. Review text: " + reviewText);
                return 0L;
            }
            reviewID = reviewDAO.create(userID, movieID, postDate, reviewTitle, rating, reviewText);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e);
        }
        return reviewID;
    }

    public Review getReview(Long reviewID) {
        if (reviewID <= 0) {
            LOGGER.error("Failed to get review. Wrong review id: " + reviewID);
            return null;
        }
        ReviewDAO reviewDAO = new ReviewDAO();
        Review review = new Review();
        try {
            review = reviewDAO.get(reviewID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e);
        }
        return review;
    }

    public void updateReview(Review review) {
        if (review.getId() <= 0) {
            LOGGER.error("Failed to update review. Wrong review id: " + review.getId());
            return;
        }
        if (review.getUserId() <= 0) {
            LOGGER.error("Failed to update review author. User id must be > 0. User id: " + review.getUserId());
            return;
        }
        if (review.getMovieId() <= 0) {
            LOGGER.error("Failed to update review movie. Movie id must be > 0. Movie id: " + review.getMovieId());
            return;
        }
        if (review.getRating() <= 0 || review.getRating() > 10) {
            LOGGER.error("Failed to update movie rating for review. Rating must be in [1-10] range. Rating: " + review.getRating());
            return;
        }
        if (review.getReviewText() == null || review.getReviewText().trim().equals("") || review.getReviewText().length() < 3) {
            LOGGER.error("Failed to update review text. Review must be not null and have at least 3 symbols. Review text: " + review.getReviewText());
            return;
        }
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            reviewDAO.update(review);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e);
        }

    }

    public boolean deleteReview(Long reviewID) {
        if (reviewID <= 0) {
            LOGGER.error("Failed to delete review. Wrong review id: " + reviewID);
            return false;
        }
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            return reviewDAO.delete(reviewID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e);
        }
        return false;
    }

}
