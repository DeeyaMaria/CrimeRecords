package com.crms;

import java.sql.*;

public class UserDatabaseUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/crimeDB";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean addUser(String userId, String username, String role, String passwordHash) {
        String sql = "INSERT INTO users(user_id, username, role, password_hash) VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, userId);
            pst.setString(2, username);
            pst.setString(3, role);
            pst.setString(4, passwordHash);
            int rows = pst.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet searchUserById(String userId) {
        String sql = "SELECT user_id, username, role FROM users WHERE user_id = ?";
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, userId);
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateUserRole(String userId, String newRole) {
        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, newRole);
            pst.setString(2, userId);
            int rows = pst.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUser(String userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, userId);
            int rows = pst.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet searchUserByUsername(String name) {
        String sql = "SELECT user_id, username, role FROM users WHERE username LIKE ?";
        try {
            Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + name + "%");
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}