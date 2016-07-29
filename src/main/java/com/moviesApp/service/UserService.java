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

    public User getUserByID(Long userID) {
        if (userID <= 0) {
            LOGGER.error("Failed to get user. User id must be > 0. User id: " + userID);
            return null;
        }
        UserDAO userDAO = new UserDAO();
        User user = new User();
        try {
            user = userDAO.get(userID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e.getMessage());
        }
        return user;
    }

    public Long createUser(String userName) {
        if (userName == null || userName.trim().equals("")) {
            LOGGER.error("Failed to create user. User name must not be null or empty. User name: " + userName);
            return 0L;
        }
        UserDAO userDAO = new UserDAO();
        Long userID = 0L;
        try {
            userID = userDAO.create(userName);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e.getMessage());
        }
        return userID;
    }

    public void updateUser(Long userID, String userName) {
        if (userID <= 0) {
            LOGGER.error("Failed to get user. User id must be > 0. User id: " + userID);
            return;
        }
        if (userName == null || userName.trim().equals("")) {
            LOGGER.error("Failed to update user. User name must not be null or empty. User name: " + userName + ". User name set to origin.");
            userName = getUserByID(userID).getName();
        }
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.update(userID, userName);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e.getMessage());
        }
    }

    public boolean deleteUser(Long userID) {
        if (userID <= 0) {
            LOGGER.error("Failed to get user. User id must be > 0. User id: " + userID);
            return false;
        }
        UserDAO userDAO = new UserDAO();
        try {
            return userDAO.delete(userID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e.getMessage());
        }
        return false;
    }

    public List<User> getAllUsers() {
        UserDAO userDAO = new UserDAO();
        List<User> users = new ArrayList<User>();
        try {
            users = userDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e.getMessage());
        }
        return users;
    }

}
