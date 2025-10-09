package com.crms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewLogsUI extends JFrame {

    private static final String[] COLUMN_NAMES = {"Log ID", "Timestamp", "User ID", "Action/Message"};

    public ViewLogsUI() {
        setTitle("Admin - System Activity Logs");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(COLUMN_NAMES, 0);
        JTable table = new JTable(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        add(new JScrollPane(table), BorderLayout.CENTER);

        try (ResultSet rs = LogDatabaseUtil.getAllLogs()) {

            if (rs == null) {
                JOptionPane.showMessageDialog(this, "Failed to connect to the database or execute query.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("log_id"),
                        rs.getTimestamp("timestamp"),
                        rs.getString("user_id"),
                        rs.getString("action")
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching log data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }
}