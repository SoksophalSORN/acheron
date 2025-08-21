package org.pexamax.acheron.views;

import org.pexamax.acheron.model.User;
import org.pexamax.acheron.dao.UserDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {
    private final AppInterface app;

    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JLabel errorLabel = new JLabel("", SwingConstants.CENTER);

    public LoginPanel(AppInterface app) {
        this.app = app;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        JButton loginBtn = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginBtn, gbc);

        JButton toSignupBtn = new JButton("Go to Signup");
        gbc.gridy = 4;
        add(toSignupBtn, gbc);

        errorLabel.setForeground(Color.RED);
        gbc.gridy = 5;
        add(errorLabel, gbc);

        // Actions
        loginBtn.addActionListener(this::login);
        toSignupBtn.addActionListener(e -> {
            errorLabel.setText("");
            app.showSignup();
        });

        // Enter key submits
        emailField.addActionListener(this::login);
        passwordField.addActionListener(this::login);
    }

    private void login(ActionEvent e) {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter email and password.");
            return;
        }

        User user = UserDao.login(email, password);
        if (user == null || !user.getPassword().equals(password)) {
            errorLabel.setText("Invalid email or password.");
            return;
        }
        app.setCurrentUser(user);

        errorLabel.setText("");
        app.showChatScreen(user.getUsername());
    }
}
