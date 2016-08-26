package com.moviesApp.validation;

import java.util.List;

/**
 * Interface that should be implemented by each validator
 */
public interface Validator {

    /**
     * Validates given object
     *
     * @param object object to validate
     * @return List of String with information about invalid fields or empty List if object is valid
     */
    List<String> validate(Object object);

}
