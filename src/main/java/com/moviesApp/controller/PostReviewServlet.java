package com.moviesApp.controller;

import com.moviesApp.entities.Review;
import com.moviesApp.entities.User;
import com.moviesApp.service.ReviewService;
import com.moviesApp.validation.ReviewValidator;
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
 * Created by dsharko on 8/5/2016.
 */
public class PostReviewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = ((User) req.getSession().getAttribute("user")).getId();
        Long movieId = Long.valueOf(req.getParameter("movieID"));
        String reviewTitle = req.getParameter("reviewTitle");
        Integer userRating = Integer.valueOf(req.getParameter("userRating"));
        String reviewText = req.getParameter("reviewText");
        Date postDate = new Date(new java.util.Date().getTime());

        Review review = new Review();
        review.setUserId(userId);
        review.setMovieId(movieId);
        review.setTitle(reviewTitle);
        review.setRating(userRating);
        review.setReviewText(reviewText);
        review.setPostDate(postDate);

        // TODO validate!!!
        Validator validator = new ReviewValidator();
        List<String> errors = validator.validate(review);

        if (errors.isEmpty()) {
            ReviewService reviewService = new ReviewService();
            reviewService.createReview(userId, movieId, postDate, reviewTitle, userRating, reviewText);
            req.getSession().removeAttribute("reviewError");
//            req.setAttribute("reviewError", errors);
//            resp.sendRedirect(req.getParameter("from"));
//            req.getRequestDispatcher(req.getParameter("from")).forward(req, resp);
//            System.out.println(req.getRequestURL() + req.getQueryString());
//            resp.setHeader("Refresh", "0; URL=http://your-current-page");
//            resp.sendRedirect(req.getParameter("from"));
//            req.getRequestDispatcher(req.getContextPath()+req.getParameter("from")).forward(req, resp);
//            String[] str = req.getParameter("from_").split("\\.");// TODO I'll fix this shit later
//            System.out.println(req.getContextPath() + str[0] + "s?" + req.getParameter("from"));
//            resp.sendRedirect(req.getContextPath() + str[0] + "s?" + req.getParameter("from"));
        } else {
            req.getSession().setAttribute("reviewError", errors);
            req.getRequestDispatcher("/movie.jsp").forward(req, resp);
        }




    }
}
