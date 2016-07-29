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

    public Long addMovie(String movieName, String director, Date releaseDate, String trailerUrl, Double rating, String description) {
        if (movieName == null || movieName.trim().equals("")) {
            LOGGER.error("Null or empty movieName during adding new Movie. Movie name: " + movieName);
            return 0L;
        }
        // TODO check release date
        if (rating <= 0 || rating > 10) {
            LOGGER.error("Rating is not in the 1-10 range. Rating: " + rating);
            return 0L;
        }
        MovieDAO movieDAO = new MovieDAO();
        Long movieID = 0L;
        try {
            movieID = movieDAO.create(movieName, director, releaseDate, trailerUrl, rating, description);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e.getMessage());
        }
        return movieID;
    }

    public Movie getMovieByID(Long ID) {
        if (ID < 1) {
            LOGGER.error("Failed to get movie. Wrong id: " + ID);
            return null;
        }
        MovieDAO movieDAO = new MovieDAO();
        Movie movie = null;
        try {
            movie = movieDAO.get(ID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e.getMessage());
        }
        return movie;
    }

    public void updateMovie(Long movieID, String movieName, String director, Date releaseDate, String trailerUrl, Double rating, String description) {
        MovieDAO movieDAO = new MovieDAO();
        try {
            if (movieID <= 0) {
                LOGGER.error("Failed to update movie. Wrong movieID: " + movieID);
                return;
            }
            if (movieName == null || movieName.trim().equals("")) {
                LOGGER.warn("Null or empty movieName during adding new Movie. Movie name: " + movieName + ". Name set to origin.");
                movieName = getMovieByID(movieID).getMovieName();
            }
            if (rating <= 0 || rating > 10) {
                LOGGER.warn("Rating is not in the allowed [1-10] range. Rating: " + rating + ". Rating set to origin.");
                rating = getMovieByID(movieID).getRating();
            }
            movieDAO.update(movieID, movieName, director, releaseDate, trailerUrl, rating, description);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e.getMessage());
        }
    }

    public boolean deleteMovie(Long movieID) {
        if (movieID < 1) {
            LOGGER.error("Failed to delete movie. Wrong id: " + movieID);
            return false;
        }
        MovieDAO movieDAO = new MovieDAO();
        try {
            return movieDAO.delete(movieID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e.getMessage());
        }
        return false;
    }

    public List<Movie> getAllMovies() {
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> movies = new ArrayList<Movie>();
        try {
            movies = movieDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle. aka column not found etc.
            LOGGER.error("SQLException: " + e.getMessage());
        }
        return movies;
    }
}
