package com.moviesApp.daoLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by dsharko on 7/28/2016.
 */
public class ConnectionManager {

    private static final String SERVER_NAME = "//localhost:3306/";
    private static final String DB_NAME = "moviedb"; // TODO create properties file
    private static final String USER = "root";
    private static final String PASSWORD = "Htlbcrf2402_";
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
        connection = DriverManager.getConnection("jdbc:mysql:" + SERVER_NAME + DB_NAME, USER, PASSWORD);
        if (connection != null) {
            System.out.println("connection not null");
        }
        return connection;
    }
}
