package com.moviesApp.daoLayer;

import com.moviesApp.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 7/29/2016.
 */
public class UserDAO {

    private final String SQL_CREATE_USER = "INSERT INTO USER (user_name) VALUES (?)";
    private final String SQL_GET_USER = "SELECT * FROM USER WHERE ID = ?";
    private final String SQL_DELETE_USER = "DELETE FROM USER WHERE ID = ?";
    private final String SQL_GET_ALL_USERS = "SELECT * FROM USER";
    private final String SQL_UPDATE_USER = "UPDATE USER SET user_name = ? WHERE ID = ?";

    public Long create(String userName) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, userName);
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        Long userID = 0L;
        if (resultSet.next()) {
            userID = resultSet.getLong(1);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return userID;
    }

    public User get(Long userID) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_USER);
        statement.setLong(1, userID);
        ResultSet resultSet = statement.executeQuery();
        User user = new User();
        if (resultSet.next()) {
            user.setId(resultSet.getLong("ID"));
            user.setName(resultSet.getString("user_name"));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return user;
    }

    public void update(User user) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER);
        statement.setString(1, user.getName());
        statement.setLong(2, user.getId());
        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public boolean delete(Long userID) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER);
        statement.setLong(1, userID);
        int afterUpdate = statement.executeUpdate();
        if (afterUpdate >= 1) {
            statement.close();
            connection.close();
            return true;
        } // TODO repeated code. mb make some abstract DAO?..
        statement.close();
        connection.close();
        return false;
    }

    public List<User> getAll() throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_USERS);
        ResultSet resultSet = statement.executeQuery();
        List<User> users = new ArrayList<User>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("ID"));
            user.setName(resultSet.getString("user_name"));
            users.add(user);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return users;
    }

}
