package org.pexamax.acheron;

import org.pexamax.acheron.views.AppInterface;
import org.pexamax.acheron.views.AuthInterface;

import org.pexamax.acheron.dao.QueryTemplate;

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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    private static JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        QueryTemplate.set(jdbcTemplate);

        User user;
        SwingUtilities.invokeLater(() -> new AppInterface(user).setVisible(true));

        SpringApplication.run(Main.class, args);
    }
}
