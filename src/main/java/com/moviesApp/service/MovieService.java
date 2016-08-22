package com.moviesApp.service;

import com.moviesApp.daoLayer.MovieDAO;
import com.moviesApp.entities.Movie;
import com.moviesApp.entities.PagedEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dsharko on 7/28/2016.
 */
public class MovieService {

    private static final Logger LOGGER = LogManager.getLogger();

    public Long addMovie(String movieName, String director, Date releaseDate, String posterURL, String trailerUrl, Double rating, String description) throws SQLException {
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
            movieDAO.update(movie);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

    public boolean deleteMovie(Long movieID) throws SQLException {
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

    public PagedEntity getAllMoviesLimit(Integer offset, Integer noOfRecords) throws SQLException {
        MovieDAO movieDAO = new MovieDAO();
        PagedEntity pagedMovies = new PagedEntity();
        List<Movie> movies;
        Integer numberOfRecords;
        try {
            movies = movieDAO.getAllLimit(offset, noOfRecords);
            numberOfRecords = movieDAO.getNumberOfRecords();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        pagedMovies.setEntity(movies);
        pagedMovies.setNumberOfRecords(numberOfRecords);
        return pagedMovies;
    }

//    public class PagedMovies {
//
//        private List<Movie> movies;
//        private Integer numberOfRecords;
//
//        public List<Movie> getMovies() {
//            return movies;
//        }
//
//        public void setMovies(List<Movie> movies) {
//            this.movies = movies;
//        }
//
//        public Integer getNumberOfRecords() {
//            return numberOfRecords;
//        }
//
//        public void setNumberOfRecords(Integer numberOfRecords) {
//            this.numberOfRecords = numberOfRecords;
//        }
//    }

}
