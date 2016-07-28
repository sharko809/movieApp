package com.moviesApp.service;

import com.moviesApp.daoLayer.MovieDAO;
import com.moviesApp.entities.Movie;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 7/28/2016.
 */
public class MovieService {


    public Movie getMovieByID(Long ID) {
        MovieDAO movieDAO = new MovieDAO();
        Movie movie = null;
        try {
            movieDAO.get(ID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
        } catch (ClassNotFoundException e) {
            e.printStackTrace();// TODO handle
        }
        return movie;
    }

    public List<Movie> getMovies() {
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> movies = new ArrayList<Movie>();
        try {
            movies = movieDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle. aka column not found etc.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();// TODO handle
        }
        return movies;
    }
}
