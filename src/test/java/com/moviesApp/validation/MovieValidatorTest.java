package com.moviesApp.validation;

import com.moviesApp.entities.Movie;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * Created by dsharko on 8/11/2016.
 */
public class MovieValidatorTest {

    private Movie movie;
    private Movie movieNull;
    @Before
    public void setUp() throws Exception {
        movie = new Movie();
        movie.setMovieName("Title");
        movie.setDirector("Director");
        movie.setReleaseDate(null);
        movie.setPosterURL(null);
        movie.setTrailerURL(null);
        movie.setRating(2D);
        movie.setDescription(null);

        movieNull = new Movie();
        movieNull.setMovieName(null);
        movieNull.setDirector(null);
        movieNull.setReleaseDate(null);
        movieNull.setPosterURL(null);
        movieNull.setTrailerURL(null);
        movieNull.setRating(null);
        movieNull.setDescription(null);
    }

    @Test
    public void validate() throws Exception {
        Validator validator = new MovieValidator();
        List<String> result = validator.validate(movie);
        assertThat(result, is(new ArrayList<>()));

        List<String> resultNullParams = validator.validate(movieNull);
        List<String> expectedNullParams = new ArrayList<>();
        expectedNullParams.add("Title should not be empty");
        expectedNullParams.add("Director field should not be empty");
        expectedNullParams.add("NULL ratings are not allowed");
        assertThat(resultNullParams, is(expectedNullParams));

        List<String> resultNullMovie = validator.validate(null);
        List<String> expectedNullMovie = new ArrayList<>();
        expectedNullMovie.add("Attempt to validate non-Movie object in movie validator");
        assertThat(resultNullMovie, is(expectedNullMovie));

    }

}