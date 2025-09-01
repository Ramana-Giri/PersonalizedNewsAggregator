package com.news.dao;

import com.news.model.User;
import com.news.service.DBConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class UserDAOImpl implements UserDAO {
    private Connection connection;

    public UserDAOImpl() throws SQLException {
        connection = DBConnection.getConnection();
    }

    // ✅ Fetch user by username
    @Override
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

    // ✅ Add user after basic validation
    @Override
    public boolean addUser(User user) {
        if (!basicValidation(user)) {
            System.out.println("❌ User failed basic validation. Cannot add to DB.");
            return false;
        }

        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashPassword(user.getPassword())); // store hashed password
            stmt.setString(3, user.getRole());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


// inside UserDAOImpl class

    @Override
    public boolean basicValidation(User user) {
        // ---- Username check ----
        if (user.getUsername() == null || user.getUsername().trim().length() < 3) {
            System.out.println("❌ Username must be at least 3 characters.");
            return false;
        }

        // ---- Password check using Regex (Pattern + Matcher) ----
        String passwordRegex = "^(?=.*[0-9])"         // at least 1 digit
                + "(?=.*[a-z])"          // at least 1 lowercase
                + "(?=.*[A-Z])"          // at least 1 uppercase
                + "(?=.*[@#$%^&+=!])"    // at least 1 special char
                + "(?=\\S+$)"            // no whitespace
                + ".{8,}$";              // at least 8 characters

        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(user.getPassword() == null ? "" : user.getPassword());

        if (!matcher.matches()) {
            System.out.println("❌ Password must be at least 8 characters long, include uppercase, lowercase, digit, and special character.");
            return false;
        }

        // ---- Role check ----
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            System.out.println("❌ Role cannot be empty.");
            return false;
        }

        // ---- DB uniqueness check for username ----
        String sql = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("❌ Username already exists.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    // ✅ Validate user for login (check username & password match)
    @Override
    public boolean validateUser(String username, String password) {
        User dbUser = getUserByUsername(username);
        if (dbUser == null) {
            System.out.println("❌ User not found.");
            return false;
        }

        String hashedPassword = hashPassword(password);
        if (!dbUser.getPassword().equals(hashedPassword)) {
            System.out.println("❌ Invalid password.");
            return false;
        }

        return true;
    }

    // ✅ Helper: hash password with SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
