package com.moviesApp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for convenient parsing url string for parameters
 */
public class UrlParametersManager {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Parses given url for parameters
     *
     * @param url URL to parse for params
     * @return HashMap containing parameter name as key and List of parameter values as value.
     */
    public static Map<String, List<String>> getUrlParams(String url) {

        if (url == null) {
            LOGGER.error("Trying to parse null URL");
            return null;
        }

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        String[] pairedParams = url.split("&");

        for (String param : pairedParams) {
            String[] pair = param.split("=");
            String key = "";
            String value = "";

            try {
                key = URLDecoder.decode(pair[0], "UTF-8");
                if (pair.length > 1) {
                    value = URLDecoder.decode(pair[1], "UTF-8");
                }
            } catch (UnsupportedEncodingException e) {
                LOGGER.fatal("Unsupported encoding: " + e);
                throw new RuntimeException("Unsupported encoding. " + e);
            }

            List<String> values = params.get(key);
            if (values == null) {
                values = new ArrayList<String>();
                params.put(key, values);
            }
            values.add(value);

        }

        return params;

    }

}
