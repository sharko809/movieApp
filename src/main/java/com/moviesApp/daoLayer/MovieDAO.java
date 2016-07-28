package com.moviesApp.daoLayer;

import com.moviesApp.entities.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 7/28/2016.
 */
public class MovieDAO {

    private final String SQL_GET_ALL_MOVIES = "SELECT * FROM MOVIE";
    private final String SQL_GET_MOVIE_BY_ID = "SELECT * FROM MOVIE WHERE ID = ?";

    public Long create() {
        // TODO todo
        return null;
    }

    public Movie get(Long movieId) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_MOVIE_BY_ID);
        statement.setLong(1, movieId);
        ResultSet resultSet = statement.executeQuery();
        Movie movie = new Movie();
        while (resultSet.next()) {
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

    public void update(Movie movie) {
        // TODO todo
    }

    public boolean delete(Long movieId) {
        // TODO todo
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
