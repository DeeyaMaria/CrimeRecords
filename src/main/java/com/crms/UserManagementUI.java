package com.crms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
// Assuming UserDatabaseUtil is in the same package or imported

public class UserManagementUI extends JFrame implements ActionListener {

    private JTextArea displayArea;
    private JTextField userIdField, usernameField, roleField, passwordField;
    private JButton addButton, searchButton, deleteButton, updateRoleButton;

    public UserManagementUI() {
        setTitle("User Management");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Input Panel ---
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("User Details / Search"));

        userIdField = new JTextField(10);
        usernameField = new JTextField(10);
        roleField = new JTextField(10);
        passwordField = new JPasswordField(10);

        inputPanel.add(new JLabel("User ID:"));
        inputPanel.add(userIdField);
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Role:"));
        inputPanel.add(roleField);
        inputPanel.add(new JLabel("Password (New User):"));
        inputPanel.add(passwordField);

        // --- Button Panel ---
        JPanel controlPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add User");
        searchButton = new JButton("Search by ID");
        deleteButton = new JButton("Delete User");
        updateRoleButton = new JButton("Update Role");

        addButton.addActionListener(this);
        searchButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateRoleButton.addActionListener(this);

        controlPanel.add(addButton);
        controlPanel.add(searchButton);
        controlPanel.add(deleteButton);
        controlPanel.add(updateRoleButton);

        inputPanel.add(new JLabel()); // Placeholder
        inputPanel.add(controlPanel);

        add(inputPanel, BorderLayout.NORTH);

        // --- Display Panel ---
        displayArea = new JTextArea("User data will appear here...", 20, 50);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // You would implement the logic here to call UserDatabaseUtil methods
        // and update the displayArea with results.
        if (e.getSource() == addButton) {
            addNewUser();
        } else if (e.getSource() == searchButton) {
            searchUser();
        }
        // ... other button logic
    }

    // Example method using the UserDatabaseUtil
    private void addNewUser() {
        String userId = userIdField.getText();
        String username = usernameField.getText();
        String role = roleField.getText();
        String password = passwordField.getText();

        // Basic validation and HASHING are essential in a real app!
        if (userId.isEmpty() || username.isEmpty() || role.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required for adding a user.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // *** IMPORTANT: In a real application, you must hash the password (e.g., using BCrypt). ***
        String passwordHash = "MOCK_HASH_" + password; // MOCK HASH for example

        if (UserDatabaseUtil.addUser(userId, username, role, passwordHash)) {
            JOptionPane.showMessageDialog(this, "User " + username + " added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add user (check console for DB error).", "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchUser() {
        String userId = userIdField.getText();
        if (userId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a User ID to search.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (ResultSet rs = UserDatabaseUtil.searchUserById(userId)) {
            displayArea.setText(""); // Clear previous results
            if (rs.next()) {
                String result = String.format("User Found:\nID: %s\nUsername: %s\nRole: %s\n",
                        rs.getString("user_id"),
                        rs.getString("username"),
                        rs.getString("role"));
                displayArea.append(result);
            } else {
                displayArea.setText("No user found with ID: " + userId);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error during search.", "DB Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        // Remember: The caller (this method) is responsible for closing the ResultSet
        // when a method returns an open ResultSet (as per the UserDatabaseUtil design).
    }
}