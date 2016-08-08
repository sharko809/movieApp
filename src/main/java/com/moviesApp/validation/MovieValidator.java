package com.moviesApp.validation;

import com.moviesApp.entities.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dsharko on 8/3/2016.
 */
public class MovieValidator implements Validator {

    private static final Logger LOGGER = LogManager.getLogger();
//    private static final String URL_PATTERN = "_^(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(" +
//            "?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\" +
//            "d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:" +
//            "\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)(?:" +
//            "\\.(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)*(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}]{2,})))(?::\\d{2,5})?(?:/[^\\s]*)?$_iuS\n";
//    private static final String URL_PATTERN = "/^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$/";
    private Pattern pattern;
    private Matcher matcher;


    public MovieValidator() {
//        pattern = Pattern.compile(URL_PATTERN);// TODO validate URL
    }

    @Override
    public List<String> validate(Object object) {

        // TODO double check this
        List<String> errors = new ArrayList<>();

        if (object == null) {
            errors.add("Can't validate null objects");
            LOGGER.error("Attempt to validate null object in movie validator");
            return errors;
        }

        if (!(object instanceof Movie)) {
            errors.add("Attempt to validate non-Movie object in movie validator");
            LOGGER.error("Attempt to validate non-Movie object in movie validator");
            return errors;
        }

        Movie movie = (Movie) object;
        // TODO regex for valid characters
        String title = movie.getMovieName();
        if (title != null) {
            if (title.length() < 1 || title.isEmpty()) {
                errors.add("Movie title should not be empty and must contain at least 1 symbol");
            }
        } else {
            errors.add("Title should not be empty");
        }

        String director = movie.getDirector();
        if (director != null) {
            if (director.length() < 2 || director.isEmpty()) {
                errors.add("Director field should not be empty and must contain at least 2 symbol");
            }
        } else {
            errors.add("Director field should not be empty");
        }

        Date releaseDate = movie.getReleaseDate();
        // TODO dunno if it worth validating

//        String trailerURL = movie.getTrailerURL();
//        if (trailerURL != null) {
//            matcher = pattern.matcher(trailerURL);
//            if (!matcher.matches()) {
//                errors.add("Invalid URL");
//            }
//        }

        Double rating = movie.getRating();
        if (rating != null) {
            if (rating < 1) {
                errors.add("Da ty ohuel!");
            }
        }

        // TODO dunno if description worth validating

        return errors;
    }
}
