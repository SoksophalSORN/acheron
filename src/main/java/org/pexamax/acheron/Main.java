package org.pexamax.acheron;

import org.pexamax.acheron.model.User;
import org.pexamax.acheron.dao.QueryTemplate;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    @Autowired
    private static JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        QueryTemplate.set(jdbcTemplate);
        UserInterface.render();
        SpringApplication.run(Main.class, args);
    }

    static class UserInterface {
        public static User user;

        public static void render() {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Acheron | Private Messaging");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null);

                CardLayout cardLayout = new CardLayout();
                JPanel appPanel = new JPanel(cardLayout);

                // ================= MAIN APP PANEL =================
                JPanel mainPanel = new JPanel();
                mainPanel.add(new JLabel("Welcome to Acheron!"));
                JButton logoutButton = new JButton("Logout");
                mainPanel.add(logoutButton);

                logoutButton.addActionListener(e -> {
                    // Add logout logic
                    cardLayout.show(appPanel, "Login");
                });

                // ================= LOGIN AND SIGNUP PANELS =================
                JPanel loginPanel = createLoginPanelElements(cardLayout, appPanel);
                JPanel signupPanel = createSignupPanelElements(cardLayout, appPanel);

                // ================= ADD PANELS =================
                appPanel.add(loginPanel, "Login");
                appPanel.add(signupPanel, "Signup");
                appPanel.add(mainPanel, "MainPanel");

                frame.add(appPanel);
                frame.setVisible(true);
            });
        }

        private static JPanel createLoginPanelElements(CardLayout cardLayout, JPanel appPanel) {
            JPanel loginPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel loginTitle = new JLabel("Login", SwingConstants.CENTER);
            loginTitle.setFont(new Font("Arial", Font.BOLD, 18));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            loginPanel.add(loginTitle, gbc);

            JLabel emailLabel = new JLabel("Email:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            loginPanel.add(emailLabel, gbc);

            JTextField emailField = new JTextField(20);
            gbc.gridx = 1;
            gbc.gridy = 1;
            loginPanel.add(emailField, gbc);

            JLabel passwordLabel = new JLabel("Password:");
            gbc.gridx = 0;
            gbc.gridy = 2;
            loginPanel.add(passwordLabel, gbc);

            JPasswordField passwordField = new JPasswordField(20);
            gbc.gridx = 1;
            gbc.gridy = 2;
            loginPanel.add(passwordField, gbc);

            JButton loginButton = new JButton("Login");
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            loginPanel.add(loginButton, gbc);

            JButton toSignupButton = new JButton("Go to Signup");
            gbc.gridy = 4;
            loginPanel.add(toSignupButton, gbc);

            JLabel loginErrorLabel = new JLabel("", SwingConstants.CENTER);
            loginErrorLabel.setForeground(Color.RED);
            gbc.gridy = 5;
            loginPanel.add(loginErrorLabel, gbc);

            toSignupButton.addActionListener(e -> {
                loginErrorLabel.setText("");
                cardLayout.show(appPanel, "Signup");
            });

            loginButton.addActionListener(e -> {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    loginErrorLabel.setText("Please enter email and password.");
                    return;
                }

                user = User.login(email, password);
                if (user == null) {
                    loginErrorLabel.setText("Invalid email or password.");
                } else {
                    loginErrorLabel.setText("");
                    cardLayout.show(appPanel, "MainPanel");
                }
            });

            return loginPanel;
        }

        private static JPanel createSignupPanelElements(CardLayout cardLayout, JPanel appPanel) {
            JPanel signupPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel signupTitle = new JLabel("Signup", SwingConstants.CENTER);
            signupTitle.setFont(new Font("Arial", Font.BOLD, 18));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            signupPanel.add(signupTitle, gbc);

            JLabel usernameLabel = new JLabel("Username:");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            signupPanel.add(usernameLabel, gbc);

            JTextField usernameField = new JTextField(20);
            gbc.gridx = 1;
            gbc.gridy = 1;
            signupPanel.add(usernameField, gbc);

            JLabel signupEmailLabel = new JLabel("Email:");
            gbc.gridx = 0;
            gbc.gridy = 2;
            signupPanel.add(signupEmailLabel, gbc);

            JTextField signupEmailField = new JTextField(20);
            gbc.gridx = 1;
            gbc.gridy = 2;
            signupPanel.add(signupEmailField, gbc);

            JLabel signupPasswordLabel = new JLabel("Password:");
            gbc.gridx = 0;
            gbc.gridy = 3;
            signupPanel.add(signupPasswordLabel, gbc);

            JPasswordField signupPasswordField = new JPasswordField(20);
            gbc.gridx = 1;
            gbc.gridy = 3;
            signupPanel.add(signupPasswordField, gbc);

            JButton signupButton = new JButton("Signup");
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            signupPanel.add(signupButton, gbc);

            JButton toLoginButton = new JButton("Go to Login");
            gbc.gridy = 5;
            signupPanel.add(toLoginButton, gbc);

            JLabel signupErrorLabel = new JLabel("", SwingConstants.CENTER);
            signupErrorLabel.setForeground(Color.RED);
            gbc.gridy = 6;
            signupPanel.add(signupErrorLabel, gbc);

            toLoginButton.addActionListener(e -> {
                signupErrorLabel.setText("");
                cardLayout.show(appPanel, "Login");
            });

            signupButton.addActionListener(e -> {
                String username = usernameField.getText().trim();
                String email = signupEmailField.getText().trim();
                String password = new String(signupPasswordField.getPassword());

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    signupErrorLabel.setText("All fields are required.");
                    return;
                }

                user = User.register(username, email, password);
                if (user == null) {
                    signupErrorLabel.setText("Signup failed. Please try again.");
                } else {
                    signupErrorLabel.setText("");
                    // Register the user...
                    cardLayout.show(appPanel, "MainPanel");
                }
            });

            return signupPanel;
        }
    }

}
