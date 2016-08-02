package com.moviesApp.validation;

import com.moviesApp.PropertiesManager;
import com.moviesApp.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dsharko on 8/2/2016.
 */
public class RegistrationValidator implements Validator {

    private final int LOGIN_LENGTH;

    public RegistrationValidator() {
        LOGIN_LENGTH = Integer.parseInt(PropertiesManager.getProperty("login.minLength"));
    }

    @Override
    public List<String> validate(Object object) {

        User user = (User) object;
        Validator loginValidator = new LoginValidator();
        List<String> errors = loginValidator.validate(user);

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
