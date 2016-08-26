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
 * Class containing all methods to interact with reviews in database
 */
public class ReviewService {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Calls for DAO method to create a new review record
     *
     * @param userID      ID of user who submitted review
     * @param movieID     ID of movie to which this review is submitted
     * @param postDate    date when user submitted review
     * @param reviewTitle title of users review
     * @param rating      rating given by user to the movie
     * @param reviewText  text of submitted review
     * @return ID of created review. If review to some reasons hasn't been created - returns 0.
     * @throws SQLException
     */
    public Long createReview(Long userID, Long movieID, Date postDate, String reviewTitle, Integer rating, String reviewText)
            throws SQLException {
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

    /**
     * Get review with specified ID from database
     *
     * @param reviewID ID of user to be found
     * @return Review entity object if review with given ID is found in database. Otherwise returns null.
     * @throws SQLException
     */
    public Review getReview(Long reviewID) throws SQLException {
        ReviewDAO reviewDAO = new ReviewDAO();
        Review review;
        try {
            review = reviewDAO.get(reviewID);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return review;
    }

    /**
     * Get review with specified movieID from database
     *
     * @param movieID ID of movie which this review refers to
     * @return List of Review objects if found any. Otherwise returns empty List.
     * @throws SQLException
     */
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

    /**
     * Updates review data in database
     *
     * @param review review entity to update
     * @throws SQLException
     */
    public void updateReview(Review review) throws SQLException {
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            reviewDAO.update(review);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

    /**
     * Deletes review record from database
     *
     * @param reviewID ID of review to be removed from database
     * @return <b>true</b> if review has been successfully deleted. Otherwise returns <b>false</b>
     * @throws SQLException
     */
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
