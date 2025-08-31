package com.news.service;

import java.sql.SQLException;

import com.news.dao.UserDAO;
import com.news.model.User;

public class AuthService {
    private UserDAO userDAO;

    public AuthService() throws SQLException {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful! Role: " + user.getRole());
            return user;
        } else {
            System.out.println("Invalid username or password!");
            return null;
        }
    }
}
