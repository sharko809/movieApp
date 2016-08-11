package com.moviesApp.validation;

import com.moviesApp.entities.Review;
import com.moviesApp.filter.SearchFilter;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by dsharko on 8/11/2016.
 */
public class ReviewValidatorTest {

    private Review review;
    private Review reviewNull;

    @Before
    public void setUp() throws Exception {
        review = new Review();
        review.setUserId(1L);
        review.setMovieId(1L);
        review.setPostDate(new Date(new java.util.Date().getTime()));
        review.setTitle("title");
        review.setRating(1);
        review.setReviewText("review");

        reviewNull = new Review();
    }

    @Test
    public void validate() throws Exception {
        Validator validator = new ReviewValidator();
        List<String> result = validator.validate(review);
        assertThat(result, is(IsEmptyCollection.empty()));

        List<String> resultNull = validator.validate(null);
        assertThat(resultNull.size(), is(1));

        List<String> resultNullReview = validator.validate(reviewNull);
        assertThat(resultNullReview.size(), is(6));

    }

}