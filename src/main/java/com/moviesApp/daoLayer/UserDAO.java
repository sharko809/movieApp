package com.moviesApp.daoLayer;

import com.moviesApp.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 7/29/2016.
 */
public class UserDAO {

    private static final String COUNT_FOUND_ROWS = "SELECT FOUND_ROWS()";
    private static final String SQL_CREATE_USER = "INSERT INTO USER (username, login, password, isadmin) VALUES (?, ?, ?, ?)";
    private static final String SQL_GET_USER = "SELECT * FROM USER WHERE ID = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM USER WHERE ID = ?";
    private static final String SQL_GET_ALL_USERS = "SELECT * FROM USER";
    private static final String SQL_UPDATE_USER = "UPDATE USER SET username = ?, login = ?, password = ?, isadmin = ?, isbanned = ? WHERE ID = ?";
    private static final String SQL_GET_USER_BY_NAME = "SELECT * FROM USER WHERE login = ?";
    private static final String SQL_GET_ALL_USERS_WITH_LIMIT = "SELECT SQL_CALC_FOUND_ROWS * FROM USER LIMIT ?, ?";
    private static final String SQL_GET_USERS_SORTED_BY = "SELECT SQL_CALC_FOUND_ROWS * FROM USER ORDER BY @ LIMIT ?, ?";
    private Integer numberOfRecords;

    public Long create(String userName, String login, String password, Boolean isAdmin) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, userName);
        statement.setString(2, login);
        statement.setString(3, password);
        statement.setBoolean(4, isAdmin);
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

    public User getByLogin(String login) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_USER_BY_NAME);
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        User user = new User();
        if (resultSet.next()) {
            user.setId(resultSet.getLong("ID"));
            user.setName(resultSet.getString("username"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setAdmin(resultSet.getBoolean("isadmin"));
            user.setBanned(resultSet.getBoolean("isbanned"));
        } else {
            user = null;
        }
        resultSet.close();
        statement.close();
        connection.close();
        return user;
    }

    public User get(Long userID) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_USER);
        statement.setLong(1, userID);
        ResultSet resultSet = statement.executeQuery();
        User user = new User();
        if (resultSet.next()) {
            user.setId(resultSet.getLong("ID"));
            user.setName(resultSet.getString("username"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setAdmin(resultSet.getBoolean("isadmin"));
            user.setBanned(resultSet.getBoolean("isbanned"));
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
        statement.setString(2, user.getLogin());
        statement.setString(3, user.getPassword());
        statement.setBoolean(4, user.isAdmin());
        statement.setBoolean(5, user.isBanned());
        statement.setLong(6, user.getId());
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
        }
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
            user.setName(resultSet.getString("username"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setAdmin(resultSet.getBoolean("isadmin"));
            user.setBanned(resultSet.getBoolean("isbanned"));
            users.add(user);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return users;
    }

    public List<User> getAllLimit(Integer offset, Integer noOfRows) throws SQLException {
        Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_USERS_WITH_LIMIT);
        statement.setInt(1, offset);
        statement.setInt(2, noOfRows);
        ResultSet resultSet = statement.executeQuery();
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("ID"));
            user.setName(resultSet.getString("username"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setAdmin(resultSet.getBoolean("isadmin"));
            user.setBanned(resultSet.getBoolean("isbanned"));
            users.add(user);
        }
        resultSet.close();
        resultSet = statement.executeQuery(COUNT_FOUND_ROWS);
        if (resultSet.next()) {
            this.numberOfRecords = resultSet.getInt(1);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return users;
    }

    public List<User> getUsersSorted(Integer offset, Integer noOfRows, String orderBy) throws SQLException{
        List<User> users = new ArrayList<>();
        String query = makeSortQuery(SQL_GET_USERS_SORTED_BY, orderBy);
        try (Connection connection = ConnectionManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, offset);
            statement.setInt(2, noOfRows);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("username"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setAdmin(resultSet.getBoolean("isadmin"));
                user.setBanned(resultSet.getBoolean("isbanned"));
                users.add(user);
            }
            resultSet.close();
            resultSet = statement.executeQuery(COUNT_FOUND_ROWS);
            if (resultSet.next()) {
                this.numberOfRecords = resultSet.getInt(1);
            }
            resultSet.close();
        }
        return users;
    }

    public Integer getNumberOfRecords() {
        return this.numberOfRecords;
    }

    private String makeSortQuery(String query, String orderBy) {
        String[] order = query.split("@");
        return order[0] + orderBy + order[1];
    }

}
