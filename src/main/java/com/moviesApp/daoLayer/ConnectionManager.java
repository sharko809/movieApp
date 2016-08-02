package com.moviesApp.daoLayer;

import com.moviesApp.PropertiesManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by dsharko on 7/28/2016.
 */
public class ConnectionManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private static ConnectionManager instance;

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.fatal("No mysql driver found.");
            throw new RuntimeException("no driver found");
        }
        Connection connection = null;
        connection = DriverManager.getConnection(
                "jdbc:mysql:" + PropertiesManager.getProperty("serverName") + PropertiesManager.getProperty("dbName"),
                PropertiesManager.getProperty("dbUser"),
                PropertiesManager.getProperty("dbPassword"));
        return connection;
    }
}
