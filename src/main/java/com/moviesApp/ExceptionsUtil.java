package com.moviesApp;

import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class for convenient handling and displaying exceptions
 */
public class ExceptionsUtil {

    /**
     * Sends given exception to error servlet, handling error messages displaying
     *
     * @param logger    logger to write error details to
     * @param req       http request
     * @param resp      http response
     * @param forwardTo forward destination
     * @param message   your own message to write to logger and display on error page
     * @param e         thrown exception. Details will be displayed on the error page
     * @throws ServletException
     * @throws IOException
     */
    public static void sendException(Logger logger, HttpServletRequest req, HttpServletResponse resp, String forwardTo, String message, Exception e)
            throws ServletException, IOException {
        logger.error(message + " " + e);
        req.setAttribute("reqUrl", req.getRequestURI() +
                ((req.getQueryString().isEmpty()) ? "" : "?" + req.getQueryString()));
        req.setAttribute("errorDetails", message + " " + e);
        req.getRequestDispatcher(forwardTo).forward(req, resp);
    }

}
