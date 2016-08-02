package com.moviesApp.validation;

import com.moviesApp.PropertiesManager;
import com.moviesApp.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dsharko on 8/2/2016.
 */
public class RegistrationValidator implements Validator {

    private static final Logger LOGGER = LogManager.getLogger();
    private final int LOGIN_LENGTH;

    public RegistrationValidator() {
        try {
            LOGIN_LENGTH = Integer.parseInt(PropertiesManager.getProperty("login.minLength"));
        } catch (NumberFormatException e) {
            LOGGER.fatal("Can't parse property value. " + e);
            throw new RuntimeException("Can't parse property value. " + e);
        }
    }

    @Override
    public List<String> validate(Object object) {

        List<String> errors = new ArrayList<>();
        if (!(object instanceof User)) {
            errors.add("Attempt to validate non-User object in registration validator");
            LOGGER.error("Attempt to validate non-User object in registration validator");
            return errors;
        }

        User user = (User) object;
        Validator loginValidator = new LoginValidator();
        errors.addAll(loginValidator.validate(user));

        String login = user.getName();
        if (login != null) {
            if (login.trim().equals("") || login.length() < LOGIN_LENGTH) {
                errors.add("Login must not be empty and must have at least " + LOGIN_LENGTH + " characters");
            }
        } else {
            errors.add("Login must not be empty");
        }

        return errors;
    }

}
