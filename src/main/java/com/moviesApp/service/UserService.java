package com.moviesApp.service;

import com.moviesApp.daoLayer.UserDAO;
import com.moviesApp.entities.PagedEntity;
import com.moviesApp.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * Class containing all methods to interact with users in database
 */
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Get user with specified ID from database
     *
     * @param userID ID of user to be found
     * @return User entity object if user with given ID is found in database. Otherwise returns null.
     * @throws SQLException
     */
    public User getUserByID(Long userID) throws SQLException {
        if (userID == null) {
            return null;
        }
        UserDAO userDAO = new UserDAO();
        User user;
        try {
            user = userDAO.get(userID);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return user;
    }

    /**
     * Get for user with specified login from database
     *
     * @param login login of user to be found
     * @return User entity object if user with given login is found in database. Otherwise returns null.
     * @throws SQLException
     */
    public User getUserByLogin(String login) throws SQLException {
        UserDAO userDAO = new UserDAO();
        User user;
        try {
            user = userDAO.getByLogin(login);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return user;
    }

    /**
     * Calls for DAO method to create a new user record
     *
     * @param userName users nickname that will be displayed to other users
     * @param login    user login used to log in the system. Not visible to other common users
     * @param password user password in encoded form
     * @param isAdmin  use <b>true</b> if you want to grant user admin rights
     * @return ID of created user. If user to some reasons hasn't been created - returns 0.
     * @throws SQLException
     */
    public Long createUser(String userName, String login, String password, Boolean isAdmin) throws SQLException {
        UserDAO userDAO = new UserDAO();
        Long userID;
        try {
            userID = userDAO.create(userName, login, password, isAdmin);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return userID;
    }

    /**
     * Updates user data in database
     *
     * @param user user entity to update
     * @throws SQLException
     */
    public void updateUser(User user) throws SQLException {
        UserDAO userDAO = new UserDAO();
        try {
            userDAO.update(user);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

    /**
     * Deletes user record from database
     *
     * @param userID ID of user to be removed from database
     * @return <b>true</b> if user has been successfully deleted. Otherwise returns <b>false</b>
     * @throws SQLException
     */
    public boolean deleteUser(Long userID) throws SQLException {
        UserDAO userDAO = new UserDAO();
        try {
            return userDAO.delete(userID);
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
    }

    /**
     * Returns records for all users in database
     *
     * @return List of User objects if any found. Otherwise returns an empty list
     * @throws SQLException
     */
    public List<User> getAllUsers() throws SQLException {
        UserDAO userDAO = new UserDAO();
        List<User> users;
        try {
            users = userDAO.getAll();
        } catch (SQLException e) {
            LOGGER.error("SQLException: " + e);
            throw new SQLException(e);
        }
        return users;
    }

    /**
     * Method used for pagination. Using offset and desired number of records returns some part of users from database.
     *
     * @param offset       starting position of select query
     * @param numberOfRows desired number of records per page
     * @return PagedEntity object storing List of User objects in given range and Number of Records in database if
     * any users found. Otherwise returns PagedEntity object with empty list and null records value
     * @throws SQLException
     * @see PagedEntity
     */
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

    /**
     * Method used for pagination search result. Using offset and desired number of records returns some part of users from database.
     *
     * @param offset       starting position of select query
     * @param numberOfRows desired number of records per page
     * @param sortBy       fields by which soring is performed
     * @param isDesc       <b>true</b> if you want descending sorting
     * @return PagedEntity object storing List of User objects in given range and Number of Records in database if
     * any users found. Otherwise returns PagedEntity object with empty list and null records value
     * @throws SQLException
     * @see PagedEntity
     */
    public PagedEntity getUsersSorted(Integer offset, Integer numberOfRows, String sortBy, Boolean isDesc) throws SQLException {
        UserDAO userDAO = new UserDAO();
        PagedEntity pagedUsers = new PagedEntity();
        List<User> users;
        Integer numberOfRecords;
        try {
            users = userDAO.getUsersSorted(offset, numberOfRows, sortBy, isDesc);
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
