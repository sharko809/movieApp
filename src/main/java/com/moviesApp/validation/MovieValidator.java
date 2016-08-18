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
    private static final String URL_PATTERN = "^(https?:\\\\/\\\\/)?([\\\\da-z\\\\.-]+)\\\\.([a-z\\\\.]{2,6})([\\\\/\\\\w \\\\.-]*)*\\\\/?$";
    private static final String TITLE_PATTERN = "[a-zA-zа-яА-я0-9]+([ '-][a-zA-Zа-яА-Я0-9]+)*";
    private static final String DIRECTOR_PATTERN = "[a-zA-zа-яА-я]+([ '-][a-zA-Zа-яА-Я]+)*";
    private static final String DESCRIPTION_PATTERN = "[a-zA-zа-яА-я0-9@()!.,+&=?:\\\\-\\\\\"']+([ '-][a-zA-Zа-яА-Я0-9@()!.,+&=?:\\\\\"'\\\\-]+)*";
    private static final String DATE_FORMAT = "";
    private Pattern titlePattern;
    private Pattern directorPattern;
    private Pattern descriptionPattern;
    private Pattern urlPattern;
    private Matcher matcher;


    public MovieValidator() {
        titlePattern = Pattern.compile(TITLE_PATTERN);
        directorPattern = Pattern.compile(DIRECTOR_PATTERN);
        descriptionPattern = Pattern.compile(DESCRIPTION_PATTERN);
        urlPattern = Pattern.compile(URL_PATTERN);// TODO validate URL
    }

    @Override
    public List<String> validate(Object object) {
        List<String> errors = new ArrayList<>();

        if (!(object instanceof Movie)) {
            errors.add("Attempt to validate non-Movie object in movie validator");
            LOGGER.error("Attempt to validate non-Movie object in movie validator");
            return errors;
        }

        Movie movie = (Movie) object;
        String title = movie.getMovieName();
        matcher = titlePattern.matcher(title);
        if (title != null) {
            if (title.length() < 1 || title.isEmpty()) {
                errors.add("Movie title should not be empty and must contain at least 1 symbol");
            } else if (!matcher.matches()) {
                errors.add("Invalid movie title");
            }
        } else {
            errors.add("Title should not be empty");
        }

        String director = movie.getDirector();
        matcher = directorPattern.matcher(director);
        if (director != null) {
            if (director.length() < 2 || director.isEmpty()) {
                errors.add("Director field should not be empty and must contain at least 2 symbol");
            } else if (!matcher.matches()) {
                errors.add("Invalid director name");
            }
        } else {
            errors.add("Director field should not be empty");
        }

        Date releaseDate = movie.getReleaseDate();
        // TODO dunno if it worth validating
        if (releaseDate != null) {

        } else {
            errors.add("Date must not be empty");
        }

        String trailerURL = movie.getTrailerURL();
        String posterURL = movie.getPosterURL();
        matcher = urlPattern.matcher(trailerURL);
        if (trailerURL != null) {
            if (!trailerURL.isEmpty()) {
                if (!matcher.matches()) {
//                    errors.add("Invalid trailer URL");
                }
            }
        }
        matcher = urlPattern.matcher(posterURL);
        if (posterURL != null) {
            if (!posterURL.isEmpty()) {
                if (!matcher.matches()) {
//                    errors.add("Invalid poster URL");
                }
            }
        }

        Double rating = movie.getRating();
        if (rating != null) {
            if (rating < 0) {
                errors.add("Negative ratings are not allowed");
            }
        } else {
            errors.add("NULL ratings are not allowed");
        }

        String description = movie.getDescription();
        matcher = descriptionPattern.matcher(description);
        if (description != null) {
            if (!description.isEmpty()) {
                if (!matcher.matches()) {
                    errors.add("Valid symbols for description are latin, cyrillic, numbers and @()!?&+-_.,:;\"\'=");
                }
            }
        }

        return errors;
    }
}
