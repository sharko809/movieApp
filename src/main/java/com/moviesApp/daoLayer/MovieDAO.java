package com.moviesApp.daoLayer;

import com.moviesApp.entities.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 7/28/2016.
 */
public class MovieDAO {

    private final String SQL_GET_ALL_MOVIES = "SELECT * FROM MOVIE";
    private final String SQL_GET_MOVIE_BY_ID = "SELECT * FROM MOVIE WHERE ID = ?";
    private final String SQL_ADD_MOVIE = "INSERT INTO MOVIE (movie_name, director, release_date, trailer_url, rating, description) VALUES (?, ?, ?, ?, ?, ?)";
    private final String SQL_UPDATE_MOVIE = "UPDATE MOVIE SET " +
            "movie_name = ?, " +
            "director = ?, " +
            "release_date = ?, " +
            "trailer_url = ?, " +
            "rating = ?, " +
            "description = ? WHERE ID = ?";
    private final String SQL_DELETE_MOVIE = "DELETE FROM MOVIE WHERE ID = ?";

    public Long create(String movieName, String director, Date releaseDate, String trailerUrl, Double rating, String description) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_ADD_MOVIE, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, movieName);
        statement.setString(2, director);
        statement.setDate(3, releaseDate);
        statement.setString(4, trailerUrl);
        statement.setDouble(5, rating);
        statement.setString(6, description);
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        Long movieID = 0L;
        if (resultSet.next()) {
            movieID = resultSet.getLong(1);
        }
        resultSet.close();// TODO may be I should put this all in extra try/catch?
        statement.close();
        connection.close();
        return movieID;
    }

    public Movie get(Long movieId) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_MOVIE_BY_ID);
        statement.setLong(1, movieId);
        ResultSet resultSet = statement.executeQuery();
        Movie movie = new Movie();
        if (resultSet.next()) {
            movie.setId(resultSet.getLong("ID"));
            movie.setMovieName(resultSet.getString("movie_name"));
            movie.setDirector(resultSet.getString("director"));
            movie.setReleaseDate(resultSet.getDate("release_date"));
            movie.setTrailerURL(resultSet.getString("trailer_url"));
            movie.setRating(resultSet.getDouble("rating"));
            movie.setDescription(resultSet.getString("description"));
        }
        resultSet.close();
        statement.close();
        connection.close();

        return movie;
    }

    public void update(Long movieID, String movieName, String director, Date releaseDate, String trailerUrl, Double rating, String description) throws SQLException {
        Movie movie = get(movieID);
        movie.setMovieName(movieName);
        movie.setDirector(director);
        movie.setReleaseDate(releaseDate);
        movie.setTrailerURL(trailerUrl);
        movie.setRating(rating);
        movie.setDescription(description);
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MOVIE);
        statement.setString(1, movie.getMovieName());
        statement.setString(2, movie.getDirector());
        statement.setDate  (3, movie.getReleaseDate());
        statement.setString(4, movie.getTrailerURL());
        statement.setDouble(5, movie.getRating());
        statement.setString(6, movie.getDescription());
        statement.setLong  (7, movieID);
        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public boolean delete(Long movieID) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MOVIE);
        statement.setLong(1, movieID);
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

    public List<Movie> getAll() throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_MOVIES);
        ResultSet resultSet = statement.executeQuery();
        List<Movie> movies = new ArrayList<Movie>();
        while (resultSet.next()) {
            Movie movie = new Movie();
            movie.setId(resultSet.getLong("ID"));
            movie.setMovieName(resultSet.getString("movie_name"));
            movie.setDirector(resultSet.getString("director"));
            movie.setReleaseDate(resultSet.getDate("release_date"));
            movie.setTrailerURL(resultSet.getString("trailer_url"));
            movie.setRating(resultSet.getDouble("rating"));
            movie.setDescription(resultSet.getString("description"));
            movies.add(movie);
        }
        resultSet.close();
        statement.close();
        connection.close();

        return movies;
    }

}
