package com.moviesApp.daoLayer;

import com.moviesApp.entities.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 7/29/2016.
 */
public class ReviewDAO {

    private static final String SQL_CREATE_REVIEW = "INSERT INTO REVIEW " +
            "(userid, movieid, postdate, reviewtitle, rating, reviewtext) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_GET_REVIEW = "SELECT * FROM REVIEW WHERE ID = ?";
    private static final String SQL_DELETE_REVIEW = "DELETE FROM REVIEW WHERE ID = ?";
    private static final String SQL_UPDATE_REVIEW = "UPDATE REVIEW SET " +
            "userID = ?, " +
            "movieID = ?, " +
            "postdate = ?, " +
            "reviewtitle = ?," +
            "rating = ?, " +
            "reviewtext = ? WHERE ID = ?";
    private static final String SQL_GET_REVIEWS_BY_MOVIE_ID = "SELECT * FROM REVIEW WHERE movieid = ?";

    public Long create(Long userID, Long movieID, Date postDate, String reviewTitle, Integer rating, String reviewText) throws SQLException {
        Long reviewID = 0L;
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_REVIEW, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, userID);
            statement.setLong(2, movieID);
            statement.setDate(3, postDate);
            statement.setString(4, reviewTitle);
            statement.setInt(5, rating);
            statement.setString(6, reviewText);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                reviewID = resultSet.getLong(1);
            }
            resultSet.close();
        }
        return reviewID;
    }

    public Review get(Long reviewID) throws SQLException {
        Review review = new Review();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_REVIEW)) {
            statement.setLong(1, reviewID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                review = parseReviewResultSet(resultSet);
            }
            resultSet.close();
        }
        return review;
    }

    public void update(Review review) throws SQLException {
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_REVIEW)) {
            statement.setLong(1, review.getUserId());
            statement.setLong(2, review.getMovieId());
            statement.setDate(3, review.getPostDate());
            statement.setString(4, review.getTitle());
            statement.setInt(5, review.getRating());
            statement.setString(6, review.getReviewText());
            statement.setLong(7, review.getId());
            statement.executeUpdate();
        }
    }

    public List<Review> getReviewsByMovieId(Long movieID) throws SQLException {
        List<Review> reviews = new ArrayList<Review>();
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_REVIEWS_BY_MOVIE_ID)) {
            statement.setLong(1, movieID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Review review;
                review = parseReviewResultSet(resultSet);
                reviews.add(review);
            }
            resultSet.close();
        }
        return reviews;
    }

    public boolean delete(Long reviewID) throws SQLException {
        try (Connection connection = ConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_REVIEW)) {
            statement.setLong(1, reviewID);
            int afterUpdate = statement.executeUpdate();
            if (afterUpdate >= 1) {
                return true;
            }
        }
        return false;
    }

    private static Review parseReviewResultSet(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getLong("id"));
        review.setUserId(resultSet.getLong("userid"));
        review.setMovieId(resultSet.getLong("movieid"));
        review.setPostDate(resultSet.getDate("postdate"));
        review.setTitle(resultSet.getString("reviewtitle"));
        review.setRating(resultSet.getInt("rating"));
        review.setReviewText(resultSet.getString("reviewtext"));
        return review;
    }

}
