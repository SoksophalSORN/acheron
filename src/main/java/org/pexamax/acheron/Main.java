package org.pexamax.acheron;

import org.pexamax.acheron.views.AppInterface;

import org.pexamax.acheron.service.UserService;

import org.pexamax.acheron.model.User;

import javax.swing.*;

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
        UserService userService = context.getBean(UserService.class);
        appInterface[0].setUserService(userService);

    }
}
