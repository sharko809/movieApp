package com.moviesApp;

import com.sun.tools.javac.util.Log;
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

    public static String getProperty(String propName) {

        Properties properties = new Properties();
        InputStream inputStream = null;

        String fileName = "movieApp.properties";
        inputStream = PropertiesManager.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            System.out.println("Unable to find " + fileName);
            throw new RuntimeException("Unable to find " + fileName);
        }
        String prop = "";
        try {
            properties.load(inputStream);
            prop = properties.getProperty(propName);// TODO: problem here. It may return null. Think of it
        } catch (IOException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("IOException" + e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace(); // TODO handle
                    LOGGER.error("IOException" + e.getMessage());
                }
            }
        }
        return prop;
    }

}
