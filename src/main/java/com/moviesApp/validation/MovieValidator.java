package com.moviesApp.validation;

import com.moviesApp.entities.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for validating Movie entity
 */
public class MovieValidator implements Validator {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String URL_PATTERN =
            "((?:(http|https|Http|Https|rtsp|Rtsp|ftp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&amp;\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_" +
                    "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&amp;\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+" +
                    "(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])" +
                    "|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnrwyz]" +
                    "|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eouw]" +
                    "|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4]" +
                    "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]" +
                    "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\:\\d{1,5})?)" +
                    "(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&amp;\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)";
    private static final String TITLE_PATTERN = "[a-zA-zа-яА-я0-9]+([ '-][a-zA-Zа-яА-Я0-9]+)*";
    private static final String DIRECTOR_PATTERN = "[a-zA-zа-яА-я]+([ '-][a-zA-Zа-яА-Я]+)*";
    private static final String DESCRIPTION_PATTERN = "[a-zA-zа-яА-я0-9@()!.,+&=?:\\\\-\\\\\"']+([ '-][a-zA-Zа-яА-Я0-9@()!.,+&=?:\\\\\"'\\\\-]+)*";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private Pattern titlePattern;
    private Pattern directorPattern;
    private Pattern descriptionPattern;
    private Pattern urlPattern;
    private Matcher matcher;

    public MovieValidator() {
        titlePattern = Pattern.compile(TITLE_PATTERN);
        directorPattern = Pattern.compile(DIRECTOR_PATTERN);
        descriptionPattern = Pattern.compile(DESCRIPTION_PATTERN);
        urlPattern = Pattern.compile(URL_PATTERN);
    }

    /**
     * Performs validation of Movie entity
     *
     * @param object object (of Movie class) to validate
     * @return if any of Movie object fields were invalid - returns List of Strings with description why
     * value considered invalid. If User is valid - returns empty List.
     */
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
        if (title != null) {
            matcher = titlePattern.matcher(title);
            if (title.length() < 1 || title.isEmpty()) {
                errors.add("Movie title should not be empty and must contain at least 1 symbol");
            } else if (!matcher.matches()) {
                errors.add("Invalid movie title");
            }
        } else {
            errors.add("Title should not be empty");
        }

        String director = movie.getDirector();
        if (director != null) {
            matcher = directorPattern.matcher(director);
            if (director.length() < 2 || director.isEmpty()) {
                errors.add("Director field should not be empty and must contain at least 2 symbol");
            } else if (!matcher.matches()) {
                errors.add("Invalid director name");
            }
        } else {
            errors.add("Director field should not be empty");
        }

        Date releaseDate = movie.getReleaseDate();
        if (releaseDate != null) {
            if (!validDate(releaseDate.toString())) {
                errors.add("Invalid date");
            }
        } else {
            errors.add("Date must not be empty");
        }

        String trailerURL = movie.getTrailerURL();
        String posterURL = movie.getPosterURL();
        if (trailerURL != null) {
            matcher = urlPattern.matcher(trailerURL);
            if (!trailerURL.isEmpty()) {
                if (!matcher.matches()) {
                    errors.add("Invalid trailer URL");
                }
            }
        }
        if (posterURL != null) {
            matcher = urlPattern.matcher(posterURL);
            if (!posterURL.isEmpty()) {
                if (!matcher.matches()) {
                    errors.add("Invalid poster URL");
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
        if (description != null) {
            matcher = descriptionPattern.matcher(description);
            if (!description.isEmpty()) {
                if (!matcher.matches()) {
                    errors.add("Valid symbols for description are latin, cyrillic, numbers and @()!?&+-_.,:;\"\'=");
                } else if (description.length() < 10) {
                    errors.add("Description should contain at least 10 characters");
                }
            }
        }

        return errors;
    }

    /**
     * Helper method for date validation
     *
     * @param date date to validate in sting format
     * @return <b>true</b> if date matches "yyyy-MM-dd" format. Otherwise returns <b>false</b>
     */
    private boolean validDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(date.trim());
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
