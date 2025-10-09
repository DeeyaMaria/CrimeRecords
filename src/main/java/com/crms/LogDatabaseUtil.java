package com.crms;

import java.sql.*;

public class LogDatabaseUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/crimeDB";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static ResultSet getAllLogs() {
        String sql = "SELECT * FROM system_logs ORDER BY timestamp DESC";

        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}