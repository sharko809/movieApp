package com.moviesApp.daoLayer;

import com.moviesApp.PropertiesManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by dsharko on 7/28/2016.
 */
public class ConnectionManager {

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
            e.printStackTrace();// TODO logger
            throw new RuntimeException("no driver found");
        }
        Connection connection = null;
        System.out.println("attempt connection");
        connection = DriverManager.getConnection(
                "jdbc:mysql:" + PropertiesManager.getProperty("serverName") + PropertiesManager.getProperty("dbName"),
                PropertiesManager.getProperty("dbUser"),
                PropertiesManager.getProperty("dbPassword"));
        if (connection != null) {
            System.out.println("connection not null");
        }
        return connection;
    }
}
