package org.pexamax.acheron.views;

import org.pexamax.acheron.model.User;

import org.pexamax.acheron.service.UserService;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class SignupPanel extends JPanel {
    private final AppInterface app;

    private final JTextField usernameField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JLabel errorLabel = new JLabel("", SwingConstants.CENTER);

    private UserService userService;

    public SignupPanel(AppInterface app) {
        this.app = app;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Signup", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        JButton signupBtn = new JButton("Signup");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(signupBtn, gbc);

        JButton toLoginBtn = new JButton("Go to Login");
        gbc.gridy = 5;
        add(toLoginBtn, gbc);

        errorLabel.setForeground(Color.RED);
        gbc.gridy = 6;
        add(errorLabel, gbc);

        signupBtn.addActionListener(this::signup);
        toLoginBtn.addActionListener(e -> {
            errorLabel.setText("");
            app.showLogin();
        });

        usernameField.addActionListener(this::signup);
        emailField.addActionListener(this::signup);
        passwordField.addActionListener(this::signup);
    }

    public void setUserService(UserService userService) {
        if (userService == null) {
            throw new IllegalArgumentException("UserService cannot be null");
        }
        this.userService = userService;
    }

    private void signup(ActionEvent e) {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("All fields are required.");
            return;
        }

        User user = userService.register(username, email, password);
        if (user == null) {
            errorLabel.setText("Signup failed. Username or email may already be taken.");
            return;
        }
        app.setCurrentUser(user);

        errorLabel.setText("");
        app.showChatScreen();
    }
}
