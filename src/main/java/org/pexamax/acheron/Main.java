package org.pexamax.acheron;

import org.pexamax.acheron.views.AppInterface;

import org.pexamax.acheron.dao.UserDao;

import org.pexamax.acheron.model.User;

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

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        User user = null;
        // SwingUtilities.invokeLater(() -> new AppInterface(user).setVisible(true));
        final AppInterface[] appInterface = new AppInterface[1];

        SwingUtilities.invokeLater(() -> {
            // Create the instance and store it in the array
            appInterface[0] = new AppInterface(user);
            appInterface[0].setVisible(true);
        });
        ApplicationContext context = SpringApplication.run(Main.class, args);
        UserDao userDao = context.getBean(UserDao.class);
        appInterface[0].setUserDao(userDao);

    }
}
