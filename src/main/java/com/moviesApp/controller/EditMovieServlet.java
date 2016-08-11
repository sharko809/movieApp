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
import java.util.List;

/**
 * Created by dsharko on 8/10/2016.
 */
public class EditMovieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String movieIdParam = req.getParameter("movieID");
        Long movieId = Long.valueOf(movieIdParam);

        MovieService movieService = new MovieService();
        if (movieId >= 1) {
            Movie movie = movieService.getMovieByID(movieId);
            req.setAttribute("movie", movie);
            req.getRequestDispatcher("/resources/views/editmovie.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("errorDetails", "No movie found");
            resp.sendRedirect(req.getContextPath() + "/error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String director = req.getParameter("director");
        String releaseDate = req.getParameter("releaseDate");
        String posterUrl = req.getParameter("posterUrl");
        String trailerUrl = req.getParameter("trailerUrl");
        String description = req.getParameter("description");
        Long movieID = Long.valueOf(req.getParameter("movieID"));

        MovieService movieService = new MovieService();

        Movie movie = movieService.getMovieByID(movieID);
        movie.setMovieName(title);
        movie.setDirector(director);
        if (releaseDate.isEmpty()) {
            movie.setReleaseDate(new Date(new java.util.Date().getTime()));// TODO handle it in some other way
        } else {
            movie.setReleaseDate(Date.valueOf(releaseDate));// TODO check this
        }
        movie.setPosterURL(posterUrl);
        movie.setTrailerURL(trailerUrl);
        movie.setDescription(description);

        Validator validator = new MovieValidator();
        List<String> errors = validator.validate(movie);

        if (errors.isEmpty()) {
            movieService.updateMovie(movie);
            req.setAttribute("result", "Movie updated");// TODO ok, this is to be done properly. I need to properly display errors on the same page
            req.setAttribute("updMovie", movie);
            req.getRequestDispatcher("/resources/views/editmovie.jsp").forward(req, resp);
        } else {
            req.setAttribute("result", errors);
            req.setAttribute("updMovie", movie);
//            req.setAttribute("movie", movie);
            req.getRequestDispatcher("/resources/views/editmovie.jsp").forward(req, resp);
        }


    }
}
