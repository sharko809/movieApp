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
public class LoginValidator implements Validator {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final int PASSWORD_LENGTH;
    private Pattern pattern;
    private Matcher matcher;

    public LoginValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
        try {
            PASSWORD_LENGTH = Integer.parseInt(PropertiesManager.getProperty("password.minLength"));
        } catch (NumberFormatException e) {
            LOGGER.fatal("Can't parse property value. " + e);
            throw new RuntimeException("Can't parse property value. " + e);
        }
    }

    @Override
    public List<String> validate(Object object) {

        List<String> errors = new ArrayList<>();

        if (!(object instanceof User)) {
            errors.add("Attempt to validate non-User object in login validator");
            LOGGER.error("Attempt to validate non-User object in login validator");
            return errors;
        }

        User user = (User) object;

        if (user.getLogin() != null) {
            matcher = pattern.matcher(user.getLogin());
            if (!matcher.matches()) {
                errors.add("Please, enter a valid email");
            }
        } else {
            errors.add("Please, enter a valid email");
        }

        String password = user.getPassword();
        if (password != null) {
            if (password.trim().equals("") || password.length() < PASSWORD_LENGTH) {
                errors.add("Password should not be empty and must have at least " + PASSWORD_LENGTH + " characters");
            }
        } else {
            errors.add("Password should not be empty and must have at least " + PASSWORD_LENGTH + " characters");
        }

        return errors;
    }

}
