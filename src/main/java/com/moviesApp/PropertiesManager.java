package com.moviesApp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dsharko on 7/29/2016.
 */
public class PropertiesManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private final static String FILE_NAME = "movieApp.properties";

    /**
     * This method returns value of the property with specified property name.
     *
     * @param propName Name of the property which value you want to get
     * @return String value of specified property or null if property is not found
     */
    public static String getProperty(String propName) {

        if (propName == null || propName.trim().equals("")) {
            LOGGER.fatal("No such property name: " + propName);
            throw new RuntimeException("No such property name found: " + propName);
        }

        Properties properties = new Properties();
        InputStream inputStream = PropertiesManager.class.getClassLoader().getResourceAsStream(FILE_NAME);
        if (inputStream == null) {
            LOGGER.fatal("Unable to find file: " + FILE_NAME);
            throw new RuntimeException("Unable to find file: " + FILE_NAME);
        }
        String prop = "";
        try {
            properties.load(inputStream);
            prop = properties.getProperty(propName);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.fatal("Seems properties file is missing. That's totally not my fault, sorry. " + e);
            throw new RuntimeException("Seems properties file is missing. That's totally not my fault, sorry. " + e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("Error while closing input stream. " + e);
            }
        }
        return prop;
    }

}
