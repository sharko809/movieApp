package com.moviesApp.service;

import com.moviesApp.daoLayer.MovieDAO;
import com.moviesApp.entities.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 7/28/2016.
 */
public class MovieService {

    private static final Logger LOGGER = LogManager.getLogger();

    public Long addMovie(String movieName, String director, Date releaseDate, String posterURL, String trailerUrl, Double rating, String description) throws SQLException {
        if (movieName == null || movieName.trim().equals("")) {
            LOGGER.error("Null or empty movieName during adding new Movie. Movie name: " + movieName);
            return 0L;
        }
        // TODO check release date
//        if (rating <= 0 || rating > 10) {
//            LOGGER.error("Rating is not in the 1-10 range. Rating: " + rating);
//            return 0L;
//        }
        MovieDAO movieDAO = new MovieDAO();
        Long movieID = 0L;
        try {
            movieID = movieDAO.create(movieName, director, releaseDate, posterURL, trailerUrl, rating, description);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return movieID;
    }

    public Movie getMovieByID(Long ID) throws SQLException {
        if (ID < 1) {
            LOGGER.error("Failed to get movie. Wrong id: " + ID);
            return null;
        }
        MovieDAO movieDAO = new MovieDAO();
        Movie movie = null;
        try {
            movie = movieDAO.get(ID);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return movie;
    }

    public void updateMovie(Movie movie) throws SQLException {
        MovieDAO movieDAO = new MovieDAO();
        try {
            if (movie.getId() <= 0) {// TODO proper validator. Don't validate shit here
                LOGGER.error("Failed to update movie. Wrong movieID: " + movie.getId());
                return;
            }
            if (movie.getMovieName() == null || movie.getMovieName().trim().equals("")) {
                LOGGER.warn("Null or empty movieName during adding new Movie. Movie name: " + movie.getMovieName());
                return;
            }
            if (movie.getRating() < 0 || movie.getRating() > 10) {
                LOGGER.warn("Rating is not in the allowed [0-10] range. Rating: " + movie.getRating());
                return;
            }
            movieDAO.update(movie);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

    public boolean deleteMovie(Long movieID) throws SQLException {
        if (movieID < 1) {
            LOGGER.error("Failed to delete movie. Wrong id: " + movieID);
            return false;
        }
        MovieDAO movieDAO = new MovieDAO();
        try {
            return movieDAO.delete(movieID);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

    public List<Movie> getAllMovies() throws SQLException {
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> movies = new ArrayList<Movie>();
        try {
            movies = movieDAO.getAll();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return movies;
    }
}
