package com.moviesApp.daoLayer;

import com.moviesApp.PropertiesManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for managing connection to database
 */
public class ConnectionManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private static ConnectionManager instance;

    private ConnectionManager() {
    }

    /**
     * Realization of simple singleton pattern
     *
     * @return an instance of this class. If instance is not created - creates it.
     * Otherwise - returns an existing one
     */
    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    /**
     * Performs connection to database with credentials specified in property file
     *
     * @return connection to URL specified in property file
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.fatal("No mysql driver found.");
            throw new RuntimeException("no driver found");
        }
        Connection connection;
        connection = DriverManager.getConnection(
                "jdbc:mysql:" + PropertiesManager.getProperty("serverName") + PropertiesManager.getProperty("dbName"),
                PropertiesManager.getProperty("dbUser"),
                PropertiesManager.getProperty("dbPassword"));
        return connection;
    }
}
