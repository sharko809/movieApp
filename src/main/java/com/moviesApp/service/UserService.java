package com.moviesApp.service;

import com.moviesApp.daoLayer.UserDAO;
import com.moviesApp.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 7/29/2016.
 */
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    public User getUserByID(Long userID) throws SQLException {
        if (userID == null) {
            LOGGER.error("Failed to get user. ID = null");
            return null;
        }
        if (userID <= 0) {
            LOGGER.error("Failed to get user. User id must be > 0. User id: " + userID);
            return null;
        }
        UserDAO userDAO = new UserDAO();
        User user = null;
        try {
            user = userDAO.get(userID);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return user;
    }

    public User getUserByLogin(String login) throws SQLException {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        try {
            user = userDAO.getByLogin(login);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return user;
    }

    public Long createUser(String userName, String login, String password, Boolean isAdmin) throws SQLException {
        UserDAO userDAO = new UserDAO();
        Long userID = 0L;
        try {
            userID = userDAO.create(userName, login, password, isAdmin);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return userID;
    }

    public void updateUser(User user) throws SQLException {
        if (user.getId() <= 0) {
            LOGGER.error("Failed to get user. User id must be > 0. User id: " + user.getId());// TODO necessary check?
            return;
        }
        if (user.getName() == null || user.getName().trim().equals("")) {
            LOGGER.error("Failed to update user. User name must not be null or empty. User name: " + user.getName());
            return;
        }
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.update(user);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

    public boolean deleteUser(Long userID) throws SQLException {
        if (userID <= 0) {
            LOGGER.error("Failed to get user. User id must be > 0. User id: " + userID);
            return false;
        }
        UserDAO userDAO = new UserDAO();
        try {
            return userDAO.delete(userID);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        UserDAO userDAO = new UserDAO();
        List<User> users = new ArrayList<User>();
        try {
            users = userDAO.getAll();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return users;
    }

}
