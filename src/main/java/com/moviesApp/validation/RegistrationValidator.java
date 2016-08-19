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
    private static final String USERNAME_PATTERN = "[a-zA-zа-яА-я0-9]+([ '-][a-zA-Zа-яА-Я0-9]+)*";
    private final int LOGIN_LENGTH;
    private Pattern pattern;
    private Matcher matcher;

    public RegistrationValidator() {
        try {
            LOGIN_LENGTH = Integer.parseInt(PropertiesManager.getProperty("login.minLength"));
        } catch (NumberFormatException e) {
            LOGGER.fatal("Can't parse property value. " + e);
            throw new RuntimeException("Can't parse property value. " + e);
        }
        pattern = Pattern.compile(USERNAME_PATTERN);
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

        String userName = user.getName();
        if (userName != null) {
            matcher = pattern.matcher(userName);
            if (userName.trim().equals("") || userName.length() < LOGIN_LENGTH) {
                errors.add("Login must not be empty and must have at least " + LOGIN_LENGTH + " characters");
            } else if (!matcher.matches()) {
                errors.add("Username is not valid");
            }
        } else {
            errors.add("Login must not be empty");
        }

        return errors;
    }

}
