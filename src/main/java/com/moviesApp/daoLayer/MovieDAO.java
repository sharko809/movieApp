package com.moviesApp.daoLayer;

import com.moviesApp.entities.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dsharko on 7/28/2016.
 */
public class MovieDAO {

    private final String SQL_GET_ALL = "SELECT * FROM MOVIE";
    private final String SQL_GET_MOVIE_BY_ID = "SELECT * FROM MOVIE WHERE ID = ?";

    public Long create() {
        return null;
    }

    public Movie get(Long movieId) throws SQLException, ClassNotFoundException {
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

    }

    public boolean delete(Long movieId) {
        return false;
    }

    public List<Movie> getAllMovies() throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL);
        ResultSet resultSet = statement.executeQuery();
        List<Movie> movies = new ArrayList<Movie>();
        while (resultSet.next()) {
            Movie movie = setMovieData(
                    resultSet.getLong("ID"),
                    resultSet.getString("description"),
                    resultSet.getString("director"),
                    resultSet.getString("movie_name"),
                    resultSet.getDouble("rating"),
                    resultSet.getDate("release_date"),
                    resultSet.getString("trailer_url"));
            movies.add(movie);
        }
        resultSet.close();
        statement.close();
        ConnectionManager.putConnection(connection);
        return movies;
    }


    private Movie setMovieData(Long id, String description, String director, String name, Double rating, Date release, String trailer) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setDescription(description);
        movie.setDirector(director);
        movie.setMovieName(name);
        movie.setRating(rating);
        movie.setReleaseDate(release);
        movie.setTrailerURL(trailer);
        return movie;
    }

}
