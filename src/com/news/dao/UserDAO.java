package com.news.dao;

import com.news.model.User;
import com.news.service.DBConnection;

import java.sql.*;

public class UserDAO {
    private Connection connection;

    public UserDAO() throws SQLException{
        connection = DBConnection.getConnection();
    }

    // 🔹 Existing: get user by username
    public User getUserByUsername(String username) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String uname = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");

                user = new User(id, uname, password, role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // 🔹 New: Register user
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());

            int rows = stmt.executeUpdate();
            return rows > 0; // returns true if inserted

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
