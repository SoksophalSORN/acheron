package org.pexamax.acheron.views;

import org.pexamax.acheron.model.User;
import org.pexamax.acheron.dao.UserDao;

import javax.swing.*;
import java.awt.*;

public class AppInterface extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    private LoginPanel loginPanel;
    private SignupPanel signupPanel;

    private User currentUser;

    public AppInterface(User user) {
        setTitle("Acheron | Private Messaging");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Construct panels
        loginPanel = new LoginPanel(this);
        signupPanel = new SignupPanel(this);

        mainPanel.add(loginPanel, "login");
        mainPanel.add(signupPanel, "signup");

        setContentPane(mainPanel);
        cardLayout.show(mainPanel, "login");
    }

    public void setUserDao(UserDao userDao) {
        if (userDao == null) {
            throw new IllegalArgumentException("UserDao cannot be null");
        }
        loginPanel.setUserDao(userDao);
        signupPanel.setUserDao(userDao);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    protected void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Called by login/signup panels when authentication succeeds.
    public void showChatScreen() {
        ChatMainScreen chat = new ChatMainScreen(this, currentUser);
        mainPanel.add(chat, "chat");
        cardLayout.show(mainPanel, "chat");
    }

    public void showLogin() {
        cardLayout.show(mainPanel, "login");
    }

    public void showSignup() {
        cardLayout.show(mainPanel, "signup");
    }

}
