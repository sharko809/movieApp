package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.UrlParametersManager;
import com.moviesApp.entities.Movie;
import com.moviesApp.entities.PagedEntity;
import com.moviesApp.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by dsharko on 8/8/2016.
 */
public class SearchServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");
        int page = 1;
        int recordsPerPage = 5;
        Map<String, List<String>> urlParams = UrlParametersManager.getUrlParams(req.getQueryString());
        Optional<List<String>> value = urlParams.entrySet().stream()
                .filter(params -> "searchInput".equals(params.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();
        String search = "";
        if (value.isPresent()) {
            search = value.get().get(0);
        }
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page <= 0) {
                    resp.sendRedirect("search?searchInput=" + search + "&page=1");
                    return;
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect("search?searchInput=" + search + "&page=1");
                return;
            }
        }

        MovieService movieService = new MovieService();
        PagedEntity pagedMovies;
        try {
            pagedMovies = movieService.searchMovies(search, (page - 1) * recordsPerPage, recordsPerPage);
        } catch (SQLException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
            return;
        }
        List<Movie> movies = null;
        int numberOfRecords = 1;
        if (pagedMovies != null) {
            movies = (List<Movie>) pagedMovies.getEntity();
            numberOfRecords = pagedMovies.getNumberOfRecords();
        }
        int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);

        req.setAttribute("movies", movies);
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("searchRequest", search);
        req.getRequestDispatcher("/resources/views/searchresult.jsp").forward(req, resp);
    }
}
