package com.moviesApp.controller;

import com.moviesApp.entities.Movie;
import com.moviesApp.service.MovieService;
import com.moviesApp.validation.MovieValidator;
import com.moviesApp.validation.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 8/3/2016.
 */
public class NewMovieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("result", null);
        req.getRequestDispatcher("/addmovie.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO something wrong happens after updating page - movie is created once more

        String title = req.getParameter("title");
        String director = req.getParameter("director");
        String releaseDate = req.getParameter("releaseDate");
        String trailerUrl = req.getParameter("trailerUrl");
        String description = req.getParameter("description");

        Movie movie = new Movie();
        movie.setMovieName(title);
        movie.setDescription(director);
        if (releaseDate.isEmpty()) {
            movie.setReleaseDate(new Date(new java.util.Date().getTime()));// TODO handle it in some other way
        } else {
            movie.setReleaseDate(Date.valueOf(releaseDate));// TODO check this
        }
        movie.setTrailerURL(trailerUrl);
        movie.setDescription(description);

        Validator validator = new MovieValidator();
        List<String> errors = validator.validate(movie);

        if (errors.isEmpty()) {
            MovieService movieService = new MovieService();
            movieService.addMovie(title, director, movie.getReleaseDate(), trailerUrl, 0D, description);
            req.getSession().setAttribute("result", "Movie " + title + " added successfully.");
            req.getRequestDispatcher("/addmovie.jsp").forward(req, resp);
        } else {
            // TODO here I should save data entered on the page
            req.getSession().setAttribute("result", errors);
            req.getRequestDispatcher("/addmovie.jsp").forward(req, resp);
        }


    }


}