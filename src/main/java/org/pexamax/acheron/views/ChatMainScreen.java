package org.pexamax.acheron.views;

import org.pexamax.acheron.dao.UserDao;
import org.pexamax.acheron.service.UserService;
import org.pexamax.acheron.model.Conversation;
import org.pexamax.acheron.model.Message;
import org.pexamax.acheron.model.User;

import java.util.TreeSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ChatMainScreen extends JPanel {
    private final AppInterface app;
    private final User currentUser;

    private final DefaultListModel<String> convListModel = new DefaultListModel<>();
    private final JList<String> conversationList = new JList<>(convListModel);

    private final JTextArea messagesArea = new JTextArea();
    private final JTextField messageField = new JTextField();
    private final JButton sendButton = new JButton("Send");

    private final JTextField startUserField = new JTextField();
    private final JButton startChatButton = new JButton("Start Chat");

    private UserDao userDao;
    private UserService userService;

    public ChatMainScreen(AppInterface app, User currentUser) {
        this.app = app;
        this.currentUser = currentUser;
        setLayout(new BorderLayout());

        // LEFT PANEL (1/4): user header + conversation list + start conversation
        JPanel left = new JPanel(new BorderLayout());
        JLabel userLabel = new JLabel("Logged in as: " + currentUser.getUsername(), SwingConstants.CENTER);
        userLabel.setFont(userLabel.getFont().deriveFont(Font.BOLD, 14f));
        left.add(userLabel, BorderLayout.NORTH);

        conversationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        left.add(new JScrollPane(conversationList), BorderLayout.CENTER);

        JPanel startPanel = new JPanel(new BorderLayout(6, 6));
        startPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        startPanel.add(new JLabel("Start chat with username:"), BorderLayout.NORTH);
        startPanel.add(startUserField, BorderLayout.CENTER);
        startPanel.add(startChatButton, BorderLayout.EAST);
        left.add(startPanel, BorderLayout.SOUTH);

        // RIGHT PANEL (3/4): messages + input
        JPanel right = new JPanel(new BorderLayout());

        messagesArea.setEditable(false);
        messagesArea.setLineWrap(true);
        messagesArea.setWrapStyleWord(true);
        right.add(new JScrollPane(messagesArea), BorderLayout.CENTER);

        JPanel input = new JPanel(new BorderLayout(6, 6));
        input.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        input.add(messageField, BorderLayout.CENTER);
        input.add(sendButton, BorderLayout.EAST);
        right.add(input, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setResizeWeight(0.25);
        add(split, BorderLayout.CENTER);

        // Populate conversations for this user
        refreshConversationList();

        // Listeners
        conversationList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedConversation();
            }
        });

        startChatButton.addActionListener(e -> startConversationWith());
        startUserField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    startConversationWith();
            }
        });

        sendButton.addActionListener(e -> sendMessage());
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    sendMessage();
            }
        });
    }

    public void setUserDao(UserDao userDao) {
        if (userDao != null)
            this.userDao = userDao;
        else throw new IllegalStateException("UserDao can't be null");
    }

    public void setUserService(UserService userService) {
        if (userService != null) {
            this.userService = userService;
        } else {
            throw new IllegalStateException("UserService can't be null");
            
        }
    }

    private void refreshConversationList() {
        convListModel.clear();
        TreeSet<Conversation> conversations = Conversation.retrieveConversations();
        for (Conversation c : conversations)
            convListModel.addElement(c.getPeer(currentUser, userDao));
    }

    private void loadSelectedConversation() {
        String peer = conversationList.getSelectedValue();
        messagesArea.setText("");
        if (peer == null)
            return;
        Conversation c = Conversation.getConversationByPeerName(currentUser, peer, userDao);
        for (Message m : c.retrieveMessages(currentUser, 20))
            messagesArea.append(m.getSenderID() + ": " + m.getContent() + "\n");
        messagesArea.setCaretPosition(messagesArea.getDocument().getLength());
    }

    private void startConversationWith() {
        String peer = startUserField.getText().trim();
        if (peer.isEmpty())
            return;

        if (userDao.getByUsername(peer) == null) {
            JOptionPane.showMessageDialog(this, "User '" + peer + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Conversation newConversation = new Conversation(
                currentUser.getUserID(),
                userDao.getByUsername(peer).getUserID());
        newConversation.save(); // Save the new conversation to the database

        refreshConversationList();
        conversationList.setSelectedValue(peer, true);
        startUserField.setText("");
    }

    private void sendMessage() {
        String text = messageField.getText().trim();
        String peer = conversationList.getSelectedValue();
        if (text.isEmpty() || peer == null)
            return;
        Conversation conv = Conversation.getConversationByPeerName(currentUser, peer, userDao);
        conv.sendMessage(new Message(
                conv.getID(),
                conv.getDestructTimer(),
                currentUser.getUserID(),
                "text",
                text.getBytes(),
                currentUser.getEdPrivateKey(),
                conv.getSymmetricKey()));
        messageField.setText("");
        loadSelectedConversation();
    }
}
