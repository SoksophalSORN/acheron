package org.pexamax.acheron;

import javax.swing.*;

public class Windows {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Acheron | Private Messaging");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton btn1 = new JButton("Login");
        btn1.setBounds(350, 250, 100, 50);
        btn1.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Login button clicked!");
        });

        frame.add(btn1);
        frame.setVisible(true);
    }
}
