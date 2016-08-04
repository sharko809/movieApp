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
 * Created by dsharko on 8/4/2016.
 */
public class UrlParametersManager {

    private static final Logger LOGGER = LogManager.getLogger();

    public static Map<String, List<String>> getUrlParams(String url) {
        Map<String, List<String>> params = new HashMap<String, List<String>>();
//        String[] urlParts = url.split("\\?");

//        if (urlParts.length > 1) {
//            String query = urlParts[1];
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
//        }
        return params;
    }

}
