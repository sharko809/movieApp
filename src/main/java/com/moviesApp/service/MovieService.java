package com.moviesApp.service;

import com.moviesApp.daoLayer.MovieDAO;
import com.moviesApp.entities.Movie;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 7/28/2016.
 */
public class MovieService {

    public Long addMovie(String movieName, String director, Date releaseDate, String trailerUrl, Double rating, String description) {
        if (movieName == null || movieName.trim().equals("")) {
            // TODO handle
            return 0L;
        }
        // TODO check release date
        if (rating <= 0 || rating > 10) {
            // TODO handle
        }
        MovieDAO movieDAO = new MovieDAO();
        Long movieID = 0L;
        try {
            movieID = movieDAO.create(movieName, director, releaseDate, trailerUrl, rating, description);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle + logger
        }
        return movieID;
    }

    public Movie getMovieByID(Long ID) {
        MovieDAO movieDAO = new MovieDAO();
        Movie movie = null;
        try {
            movie = movieDAO.get(ID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle + LOGGER
        }
        return movie;
    }

    public void updateMovie(Long movieID, String movieName, String director, Date releaseDate, String trailerUrl, Double rating, String description) {
        MovieDAO movieDAO = new MovieDAO();
        try {
            if (movieID <= 0) {
                System.out.println("Slow down. No negative ID's today...");
                return;
            }
            if (movieName == null || movieName.trim().equals("")) {
                System.out.println("Wrong name. Name set to origin.");// TODO logger
                movieName = getMovieByID(movieID).getMovieName();
            }
            if (rating <= 0 || rating > 10) {
                System.out.println("Wrong rating. Only 1-10 allowed. Rating set to origin.");// TODO logger
                rating = getMovieByID(movieID).getRating();
            }
            movieDAO.update(movieID, movieName, director, releaseDate, trailerUrl, rating, description);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
        }
    }

    public boolean deleteMovie(Long movieID) {
        MovieDAO movieDAO = new MovieDAO();
        try {
            return movieDAO.delete(movieID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
        }
        return false;
    }

    public List<Movie> getAllMovies() {
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> movies = new ArrayList<Movie>();
        try {
            movies = movieDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle. aka column not found etc. + LOGGER
        }
        return movies;
    }
}
