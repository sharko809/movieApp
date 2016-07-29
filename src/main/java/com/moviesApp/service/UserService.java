package com.moviesApp.service;

import com.moviesApp.daoLayer.UserDAO;
import com.moviesApp.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsharko on 7/29/2016.
 */
public class UserService {

    public User getUserByID(Long userID) {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        try {
            user = userDAO.get(userID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
        }
        return user;
    }

    public Long createUser(String userName) {
        UserDAO userDAO = new UserDAO();
        Long userID = 0L;
        try {
            userID = userDAO.create(userName);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
        }
        return userID;
    }

    public void updateUser(Long userID, String userName) {
        UserDAO userDAO = new UserDAO();
        try {
            if (userName == null || userName.trim().equals("")) {
                System.out.println("Update failed due to wrong name");
                return;
            }
            userDAO.update(userID, userName);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
        }
    }

    public boolean deleteUser(Long userID) {
        UserDAO userDAO = new UserDAO();
        try {
            return userDAO.delete(userID);
        } catch (SQLException e) {
            e.printStackTrace();// TODO handle
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
        }
        return users;
    }

}
