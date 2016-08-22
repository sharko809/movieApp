package com.moviesApp.service;

import com.moviesApp.daoLayer.UserDAO;
import com.moviesApp.entities.PagedEntity;
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
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.update(user);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

    public boolean deleteUser(Long userID) throws SQLException {
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

    public PagedEntity getAllUsersLimit(Integer offset, Integer numberOfRows) throws SQLException {
        UserDAO userDAO = new UserDAO();
        PagedEntity pagedUsers = new PagedEntity();
        List<User> users;
        Integer numberOfRecords;
        try {
            users = userDAO.getAllLimit(offset, numberOfRows);
            numberOfRecords = userDAO.getNumberOfRecords();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        pagedUsers.setEntity(users);
        pagedUsers.setNumberOfRecords(numberOfRecords);
        return pagedUsers;
    }

    public PagedEntity getUsersSorted(Integer offset, Integer numberOfRows, String sortBy) throws SQLException {
        UserDAO userDAO = new UserDAO();
        PagedEntity pagedUsers = new PagedEntity();
        List<User> users;
        Integer numberOfRecords;
        try {
            users = userDAO.getUsersSorted(offset, numberOfRows, sortBy);
            numberOfRecords = userDAO.getNumberOfRecords();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        pagedUsers.setEntity(users);
        pagedUsers.setNumberOfRecords(numberOfRecords);
        return pagedUsers;
    }

}
