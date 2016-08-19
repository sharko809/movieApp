package com.moviesApp.validation;

import com.moviesApp.entities.Review;
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
 * Created by dsharko on 8/5/2016.
 */
public class ReviewValidator implements Validator {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String TITLE_PATTERN = "[a-zA-zа-яА-я0-9]+([ '-][a-zA-Zа-яА-Я0-9]+)*";
    private static final String TEXT_PATTERN = "[a-zA-zа-яА-я0-9@()!.,+&=?:\\\\-\\\\\"']+([ '-][a-zA-Zа-яА-Я0-9@()!.,+&=?:\\\\\"'\\\\-]+)*";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private Pattern titlePattern;
    private Pattern textPattern;
    private Matcher matcher;

    public ReviewValidator() {
        titlePattern = Pattern.compile(TITLE_PATTERN);
        textPattern = Pattern.compile(TEXT_PATTERN);
    }

    @Override
    public List<String> validate(Object object) {
        List<String> errors = new ArrayList<>();

        if (!(object instanceof Review)) {
            errors.add("Attempt to validate invalid object in review validator");
            LOGGER.error("Attempt to validate invalid object in review validator");
            return errors;
        }

        Review review = (Review) object;

        if (review.getUserId() != null) {
            if (review.getUserId() < 1) {
                errors.add("Invalid review author. Try once more.");
            }
        } else {
            errors.add("Invalid review author. Try once more.");
        }

        if (review.getMovieId() != null) {
            if (review.getMovieId() < 1) {
                errors.add("Invalid movie reference. Try once more.");
            }
        } else {
            errors.add("Invalid movie reference. Try once more.");
        }

        Date postDate = review.getPostDate();
        if (postDate != null) {
            if (!validDate(postDate.toString())) {
                errors.add("Invalid date");
            }
        } else {
            errors.add("Date must not be empty.");
        }

        String title = review.getTitle();
        if (title != null) {
            matcher = titlePattern.matcher(title);
            if (!(title.isEmpty())) {
                if (title.length() < 3) {
                    errors.add("Review title should have at least 3 characters");
                } else if (!matcher.matches()) {
                    errors.add("Invalid characters in title");
                }
            } else {
                errors.add("Please, specify yor review title");
            }
        } else {
            errors.add("Review title should have at least 3 characters");
        }

        if (review.getRating() != null) {
            if (review.getRating() < 1 || review.getRating() > 10) {
                errors.add("Only rating in [1-10] range is allowed");
            }
        } else {
            errors.add("Only rating in [1-10] range is allowed");
        }

        String text = review.getReviewText();
        if (text != null) {
            matcher = textPattern.matcher(text);
            if (!(review.getReviewText().isEmpty())) {
                if (text.length() < 5) {
                    errors.add("Review should have at least 5 characters");
                } else if (!matcher.matches()) {
                    errors.add("Invalid characters in review");
                }
            } else {
                errors.add("Review should not be empty");
            }
        } else {
            errors.add("Review should not be empty");
        }

        return errors;
    }

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
