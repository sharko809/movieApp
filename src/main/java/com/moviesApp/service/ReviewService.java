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
            LOGGER.error("SQLException: " + e.getMessage());
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
            LOGGER.error("SQLException: " + e.getMessage());
        }
        return review;
    }

    public void updateReview(Long reviewID, Long userID, Long movieID, Date postDate, String reviewTitle, Integer rating, String reviewText) {
        if (reviewID <= 0) {
            LOGGER.error("Failed to update review. Wrong review id: " + reviewID);
            return;
        }
        if (userID <= 0) {
            LOGGER.error("Failed to update review author. User id must be > 0. User id: " + userID + ". User id set to origin.");
            userID = getReview(reviewID).getUserId();
        }
        if (movieID <= 0) {
            LOGGER.error("Failed to update review movie. Movie id must be > 0. Movie id: " + movieID + ". Movie id set to origin.");
            movieID = getReview(reviewID).getMovieId();
        }
        if (rating <= 0 || rating > 10) {
            LOGGER.error("Failed to update movie rating for review. Rating must be in [1-10] range. Rating: " + rating + ". Rating set to origin.");
            rating = getReview(reviewID).getRating();
        }
        if (reviewText == null || reviewText.trim().equals("") || reviewText.length() < 3) {
            LOGGER.error("Failed to update review text. Review must be not null and have at least 3 symbols. Review text: " + reviewText + ". Review text set to origin.");
            reviewText = getReview(reviewID).getReviewText();
        }
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            reviewDAO.update(reviewID, userID, movieID, postDate, reviewTitle, rating, reviewText);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e.getMessage());
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
            LOGGER.error("SQLException: " + e.getMessage());
        }
        return false;
    }

}
