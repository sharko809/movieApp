package com.moviesApp.controller;

import com.moviesApp.ExceptionsUtil;
import com.moviesApp.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dsharko on 8/17/2016.
 */
public class DeleteReviewServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String reviewIdParam = req.getParameter("reviewID");
        String movieIdParam = req.getParameter("movieID");
        Long reviewID = 0L;
        Long movieID = 0L;
        try {
            reviewID = Long.valueOf(reviewIdParam);
            movieID = Long.valueOf(movieIdParam);
        } catch (NumberFormatException e) {
            ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "Error parsing review or movie ID", e);
            return;
        }

        if (reviewID != null) {
            if (reviewID >= 1) {
                ReviewService reviewService = new ReviewService();
                try {
                    reviewService.deleteReview(reviewID);
                } catch (SQLException e) {
                    ExceptionsUtil.sendException(LOGGER, req, resp, "/error", "", e);
                    return;
                }
                resp.sendRedirect("editmovie?movieID=" + movieID);
            }
        }

    }
}
