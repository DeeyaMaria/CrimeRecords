package com.crms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AdminDashboardUI extends JFrame implements ActionListener {
    private JButton manageUsersBtn, viewLogsBtn, logoutBtn;

    public AdminDashboardUI() {
        setTitle("Admin Dashboard - Crime Record Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel(new BorderLayout());
        add(contentPanel);

        // Header
        JLabel header = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBackground(new Color(153, 0, 51));
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        contentPanel.add(header, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        contentPanel.add(buttonPanel, BorderLayout.CENTER);

        manageUsersBtn = createButton("Manage Users");
        viewLogsBtn = createButton("View Logs");
        logoutBtn = createButton("Logout");

        buttonPanel.add(manageUsersBtn);
        buttonPanel.add(viewLogsBtn);
        buttonPanel.add(logoutBtn);

        // Action Listeners
        manageUsersBtn.addActionListener(this);
        viewLogsBtn.addActionListener(this);
        logoutBtn.addActionListener(this);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(new Color(200, 200, 200));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 100));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
            if (src == manageUsersBtn) {
                UserManagementUI userManager = new UserManagementUI();
                userManager.setVisible(true);
            } else if (src == viewLogsBtn) {
                JOptionPane.showMessageDialog(this, "Viewing Logs...");
        } else if (src == logoutBtn) {
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to logout?", "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                new AdminLoginUI().setVisible(true);
                dispose();
            }
        }
    }
}
