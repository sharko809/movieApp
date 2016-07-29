package com.moviesApp.daoLayer;

import com.moviesApp.entities.Review;

import java.sql.*;

/**
 * Created by dsharko on 7/29/2016.
 */
public class ReviewDAO {

    private final String SQL_CREATE_REVIEW = "INSERT INTO REVIEW " +
            "(user_id, movie_id, post_date, review_title, rating, review_text) VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQL_GET_REVIEW = "SELECT * FROM REVIEW WHERE ID = ?";
    private final String SQL_DELETE_REVIEW = "DELETE FROM REVIEW WHERE ID = ?";
    private final String SQL_UPDATE_REVIEW = "UPDATE REVIEW SET " +
            "user_ID = ?, " +
            "movie_ID = ?, " +
            "post_date = ?, " +
            "review_title = ?," +
            "rating = ?, " +
            "review_text = ? WHERE ID = ?";

    public Long create(Long userID, Long movieID, Date postDate, String reviewTitle, Integer rating, String reviewText) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_CREATE_REVIEW, Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, userID);
        statement.setLong(2, movieID);
        statement.setDate(3, postDate);
        statement.setString(4, reviewTitle);
        statement.setInt(5, rating);
        statement.setString(6, reviewText);
        statement.executeUpdate();
        Long reviewID = 0L;
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            reviewID = resultSet.getLong(1);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return reviewID;
    }

    public Review get(Long reviewID) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_REVIEW);
        statement.setLong(1, reviewID);
        ResultSet resultSet = statement.executeQuery();
        Review review = new Review();
        if (resultSet.next()) {
            review.setId(resultSet.getLong("ID"));
            review.setUserId(resultSet.getLong("user_ID"));
            review.setMovieId(resultSet.getLong("movie_ID"));
            review.setPostDate(resultSet.getDate("post_date"));
            review.setTitle(resultSet.getString("review_title"));
            review.setRating(resultSet.getInt("rating"));
            review.setReviewText(resultSet.getString("review_text"));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return review;
    }

    public void update(Review review) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_REVIEW);

        statement.setLong   (1, review.getUserId());
        statement.setLong   (2, review.getMovieId());
        statement.setDate   (3, review.getPostDate());
        statement.setString (4, review.getTitle());
        statement.setInt    (5, review.getRating());
        statement.setString (6, review.getReviewText());
        statement.setLong   (7, review.getId());
        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public boolean delete(Long reviewID) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_REVIEW);
        statement.setLong(1, reviewID);
        int afterUpdate = statement.executeUpdate();
        if (afterUpdate >= 1) {
            statement.close();
            connection.close();
            return true;
        }
        statement.close();
        connection.close();
        return false;
    }

}