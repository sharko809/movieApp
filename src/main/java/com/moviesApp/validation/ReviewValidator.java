package com.moviesApp.validation;

import com.moviesApp.entities.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 8/5/2016.
 */
public class ReviewValidator implements Validator {

    private static final Logger LOGGER = LogManager.getLogger();

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

        if (review.getPostDate() == null) {
            errors.add("Date must not be empty.");// TODO think of it
        }


        if (review.getTitle() != null) {
            if (!(review.getTitle().isEmpty())) {// TODO may be add regex
                if (review.getTitle().length() < 3) {
                    errors.add("Review title should have at least 3 characters");
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


        if (review.getReviewText() != null) {
            if (review.getReviewText() != null || !(review.getReviewText().isEmpty())) {
                if (review.getReviewText().length() < 5) {
                    errors.add("Review should have at least 5 characters"); // TODO think of it
                }
            } else {
                errors.add("Review should not be empty");
            }
        } else {
            errors.add("Review should not be empty");
        }


        return errors;
    }

}
