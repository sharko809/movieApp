package com.moviesApp.controller;

import com.moviesApp.entities.Movie;
import com.moviesApp.service.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 8/8/2016.
 */
public class SearchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("searchInput");
        req.setAttribute("searchRequest", search);
        List<Movie> result = new ArrayList<>();

        if (search != null) {
            if (!search.isEmpty()) {
                MovieService movieService = new MovieService();
                List<Movie> movies = movieService.getAllMovies();
                if (!movies.isEmpty()) {
                    movies.stream()
                            .filter(m -> m.getMovieName().toLowerCase().contains(search.toLowerCase()))
                            .forEach(result::add);
                }
            }
        }

        req.setAttribute("movies", result);
        req.getRequestDispatcher("/resources/views/searchresult.jsp").forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
