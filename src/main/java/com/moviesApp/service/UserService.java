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
            LOGGER.error("SQLException: " + e);
        }
        return user;
    }

    public User getUserByLogin(String login) {
        if (login == null || login.trim().equals("") || login.trim().length() < 5) {
            LOGGER.error("Failed to get user. Login is invalid or has less than 5 characters: " + login);// TODO "if" may be unnecessary
            return null; // TODO is null ok?
        }
        UserDAO userDAO = new UserDAO();
        User user = new User();
        try {
            user = userDAO.getByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e);
        }
        return user;
    }

    public Long createUser(String userName, String login, String password) {
        if (userName == null || userName.trim().equals("")) {
            LOGGER.error("Failed to create user. User name must not be null or empty. User name: " + userName);
            return 0L;
        }
        UserDAO userDAO = new UserDAO();// TODO check everything for validity and login - for duplicates. Don't forget ty encrypt password
        Long userID = 0L;
        try {
            userID = userDAO.create(userName, login, password);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e);
        }
        return userID;
    }

    public void updateUser(User user) {
        if (user.getId() <= 0) {
            LOGGER.error("Failed to get user. User id must be > 0. User id: " + user.getId());
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
            e.printStackTrace();// TODO handle
            LOGGER.error("SQLException: " + e);
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
            LOGGER.error("SQLException: " + e);
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
            LOGGER.error("SQLException: " + e);
        }
        return users;
    }

}
