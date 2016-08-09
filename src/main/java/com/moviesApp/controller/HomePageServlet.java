package com.moviesApp.controller;

import com.moviesApp.entities.Movie;
import com.moviesApp.service.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by dsharko on 7/28/2016.
 */
public class HomePageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MovieService movieService = new MovieService();
        List<Movie> movies = movieService.getAllMovies();
        req.setAttribute("movies", movies);
        req.getRequestDispatcher("/resources/views/home.jsp").forward(req, resp);

    }

}
