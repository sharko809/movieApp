package com.moviesApp.service;

import com.moviesApp.daoLayer.MovieDAO;
import com.moviesApp.entities.Movie;
import com.moviesApp.entities.PagedEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Class containing all methods to interact with movies in database
 */
public class MovieService {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Calls for DAO method to create a new movie record
     *
     * @param movieName   desired movie title
     * @param director    movie director
     * @param releaseDate movie release date in  "yyyy-MM-dd" format
     * @param posterURL   URL leading to movie poster
     * @param trailerUrl  URL leading to embed video
     * @param rating      movie rating. Set to 0 by default
     * @param description some description for the movie
     * @return ID of created movie record in database. If record hasn't been created returns 0.
     * @throws SQLException
     */
    public Long addMovie(String movieName, String director, Date releaseDate, String posterURL, String trailerUrl, Double rating, String description) throws SQLException {
        MovieDAO movieDAO = new MovieDAO();
        Long movieID;
        try {
            movieID = movieDAO.create(movieName, director, releaseDate, posterURL, trailerUrl, rating, description);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return movieID;
    }

    /**
     * Get movie with specified ID from database
     *
     * @param movieID ID of movie to be found
     * @return Movie entity object if movie with given ID is found in database. Otherwise returns null.
     * @throws SQLException
     */
    public Movie getMovieByID(Long movieID) throws SQLException {
        MovieDAO movieDAO = new MovieDAO();
        Movie movie;
        try {
            movie = movieDAO.get(movieID);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return movie;
    }

    /**
     * Updates movie data in database
     *
     * @param movie movie entity to update
     * @throws SQLException
     */
    public void updateMovie(Movie movie) throws SQLException {
        MovieDAO movieDAO = new MovieDAO();
        try {
            movieDAO.update(movie);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

    /**
     * Deletes movie record from database
     *
     * @param movieID ID of movie to be removed from database
     * @return <b>true</b> if movie has been successfully deleted. Otherwise returns <b>false</b>
     * @throws SQLException
     */
    public boolean deleteMovie(Long movieID) throws SQLException {
        MovieDAO movieDAO = new MovieDAO();
        try {
            return movieDAO.delete(movieID);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

    /**
     * Returns records for all movies in database
     *
     * @return List of Movie objects if any found. Otherwise returns an empty list
     * @throws SQLException
     */
    public List<Movie> getAllMovies() throws SQLException {
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> movies;
        try {
            movies = movieDAO.getAll();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return movies;
    }

    /**
     * Method used for pagination. Using offset and desired number of records returns some part of movies from database.
     *
     * @param offset   starting position of select query
     * @param noOfRows desired number of records per page
     * @return PagedEntity object storing List of Movie objects in given range and Number of Records in database if
     * any movies found. Otherwise returns PagedEntity object with empty list and null records value
     * @throws SQLException
     * @see PagedEntity
     */
    public PagedEntity getAllMoviesLimit(Integer offset, Integer noOfRows) throws SQLException {
        MovieDAO movieDAO = new MovieDAO();
        PagedEntity pagedMovies = new PagedEntity();
        List<Movie> movies;
        Integer numberOfRecords;
        try {
            movies = movieDAO.getAllLimit(offset, noOfRows);
            numberOfRecords = movieDAO.getNumberOfRecords();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        pagedMovies.setEntity(movies);
        pagedMovies.setNumberOfRecords(numberOfRecords);
        return pagedMovies;
    }

    /**
     * Gets all movies with matching name from database. Method is used for paginating results as well.
     *
     * @param movieName movie name to look for
     * @param offset    starting position of select query
     * @param noOfRows  desired number of records per page
     * @return PagedEntity object storing List of Movie objects in given range and Number of Records in database if
     * any movies found. Otherwise returns PagedEntity object with empty list and null records value
     * @throws SQLException
     * @see PagedEntity
     */
    public PagedEntity searchMovies(String movieName, Integer offset, Integer noOfRows) throws SQLException {
        MovieDAO movieDAO = new MovieDAO();
        PagedEntity pagedMovies = new PagedEntity();
        List<Movie> movies;
        Integer numberOfRecords;
        try {
            movies = movieDAO.getMoviesLike(movieName, offset, noOfRows);
            numberOfRecords = movieDAO.getNumberOfRecords();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        pagedMovies.setEntity(movies);
        pagedMovies.setNumberOfRecords(numberOfRecords);
        return pagedMovies;
    }

}
