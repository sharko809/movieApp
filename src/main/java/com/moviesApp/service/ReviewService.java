package com.moviesApp.service;

import com.moviesApp.daoLayer.ReviewDAO;
import com.moviesApp.entities.Review;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by dsharko on 7/29/2016.
 */
public class ReviewService {

    public Long createReview(Long userID, Long movieID, Date postDate, String reviewTitle, Integer rating, String reviewText) {
        ReviewDAO reviewDAO = new ReviewDAO();
        Long reviewID = 0L;
        try {
            if (userID <= 0) {
                System.out.println("Fail. User id must be > 0");
                return 0L;
            }
            if (movieID <= 0) {
                System.out.println("Fail. Movie id must be > 0");
                return 0L;
            }
            if (rating <= 0 || rating > 10) {
                System.out.println("Fail. Rating must be 1-10.");
                return 0L;
            }
            if (reviewText == null || reviewText.trim().equals("") || reviewText.length() < 3) {
                System.out.println("Fail. Review must have at least 3 symbols");
                return 0L;
            }
            reviewID = reviewDAO.create(userID, movieID, postDate, reviewTitle, rating, reviewText);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
        }
        return reviewID;
    }

    public Review getReview(Long reviewID) {
        if (reviewID <= 0) {
            System.out.println("Slow down. No negative ID's today...");
            return null;
        }
        ReviewDAO reviewDAO = new ReviewDAO();
        Review review = new Review();
        try {
            review = reviewDAO.get(reviewID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
        }
        return review;
    }

    public void updateReview(Long reviewID, Long userID, Long movieID, Date postDate, String reviewTitle, Integer rating, String reviewText) {
        if (reviewID <= 0) {
            System.out.println("Slow down. No negative ID's today...");
            return;
        }
        if (userID <= 0) {
            System.out.println("Slow down. No negative ID's today... UserID set to origin.");
            userID = getReview(reviewID).getUserId();
        }
        if (movieID <= 0) {
            System.out.println("Slow down. No negative ID's today... MovieID set to origin.");
            movieID = getReview(reviewID).getMovieId();
        }
        if (rating <= 0 || rating > 10) {
            System.out.println("Fail. Rating must be 1-10. Rating set to origin.");
            rating = getReview(reviewID).getRating();
        }
        if (reviewText == null || reviewText.trim().equals("") || reviewText.length() < 3) {
            System.out.println("Fail. Review must have at least 3 symbols. Review text set to origin.");
            reviewText = getReview(reviewID).getReviewText();
        }
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            reviewDAO.update(reviewID, userID, movieID, postDate, reviewTitle, rating, reviewText);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
        }

    }

    public boolean deleteReview(Long reviewID) {
        if (reviewID <= 0) {
            System.out.println("Slow down. No negative ID's today...");
            return false;
        }
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            return reviewDAO.delete(reviewID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
        }
        return false;
    }

}
