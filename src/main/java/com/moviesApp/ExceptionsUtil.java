package com.moviesApp;

import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dsharko on 8/17/2016.
 */
public class ExceptionsUtil {

    public static void sendException(Logger logger, HttpServletRequest req, HttpServletResponse resp, String forwardTo, String message, Exception e)
            throws ServletException, IOException {
        e.printStackTrace();
        logger.error(message + " " + e);
        req.setAttribute("errorDetails", message + " " + e);
        req.getRequestDispatcher(forwardTo).forward(req, resp);
    }

}
