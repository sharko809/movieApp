package com.moviesApp.controller;

import com.moviesApp.UrlParametersManager;
import com.moviesApp.entities.Movie;
import com.moviesApp.service.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dsharko on 8/4/2016.
 */
public class MovieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Long[] movieID = new Long[1];

        UrlParametersManager.getUrlParams(req.getQueryString()).forEach((key, value) -> {
            if (key.equals("movieId")) {// TODO think if multiply params are necessary
                movieID[0] = Long.valueOf(value.get(0));
            }
        });

        MovieService movieService = new MovieService();
        Movie movie = null;
        if (movieID[0] != null) {
            movie = movieService.getMovieByID(movieID[0]);
        }

        if (movie == null) {
            req.getSession().setAttribute("errorDetails", "Something wrong has happened and movie can't be found :(");
            resp.sendRedirect(req.getContextPath() + "/error");
        } else {
            req.getSession().setAttribute("movie", movie);
            req.getRequestDispatcher("/movie.jsp").forward(req, resp);

        }

    }
}
