package com.moviesApp.service;

import com.moviesApp.daoLayer.ReviewDAO;
import com.moviesApp.entities.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 7/29/2016.
 */
public class ReviewService {

    private static final Logger LOGGER = LogManager.getLogger();

    public Long createReview(Long userID, Long movieID, Date postDate, String reviewTitle, Integer rating, String reviewText) throws SQLException {
        ReviewDAO reviewDAO = new ReviewDAO();
        Long reviewID = 0L;
        try {
            reviewID = reviewDAO.create(userID, movieID, postDate, reviewTitle, rating, reviewText);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return reviewID;
    }

    public Review getReview(Long reviewID) throws SQLException {
        ReviewDAO reviewDAO = new ReviewDAO();
        Review review = new Review();
        try {
            review = reviewDAO.get(reviewID);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return review;
    }

    public List<Review> getReviewsByMovieId(Long movieID) throws SQLException {
        ReviewDAO reviewDAO = new ReviewDAO();
        List<Review> reviews = new ArrayList<Review>();
        try {
            reviews.addAll(reviewDAO.getReviewsByMovieId(movieID));
        } catch (SQLException e) {
            LOGGER.fatal("SQLException: " + e);
            throw new SQLException(e);
        }
        return reviews;
    }

    public void updateReview(Review review) throws SQLException {
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            reviewDAO.update(review);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

    public boolean deleteReview(Long reviewID) throws SQLException {
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            return reviewDAO.delete(reviewID);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

}
