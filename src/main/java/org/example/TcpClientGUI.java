package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;

public class TcpClientGUI extends JFrame {

    private JTextArea messageArea;
    private JTextField inputField;
    private PrintWriter out;
    private BufferedReader in;

    private Socket socket;
    private String username;

    public TcpClientGUI() {
        setTitle("TCP Chat Client");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the GUI components
        setupGUIComponents();

        // Start the connection setup
        setupConnection();
    }

    private void setupGUIComponents() {
        // Message area
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(messageScrollPane, BorderLayout.CENTER);

        // Input field for messages
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    String message = inputField.getText();
                    if (!message.isEmpty()) {
                        out.println(message);
                        inputField.setText(""); // Clear input field
                    }
                }
            }
        });
        add(inputField, BorderLayout.SOUTH);

        // Display the username dialog
        username = JOptionPane.showInputDialog(this, "Enter your username:", "Username", JOptionPane.PLAIN_MESSAGE);
        if (username != null && !username.trim().isEmpty()) {
            setTitle("User: " + username);
        }
    }

    private void setupConnection() {
        try {
            socket = new Socket("localhost", 8080); // Connect to the server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Send the username to the server
            out.println(username);

            // Start a new thread to listen for messages from the server
            new Thread(new ServerListener()).start();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the server.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Thread to listen for incoming messages from the server
    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    messageArea.append(message + "\n");
                    messageArea.setCaretPosition(messageArea.getDocument().getLength()); // Scroll to the bottom
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TcpClientGUI clientGUI = new TcpClientGUI();
            clientGUI.setVisible(true);
        });
    }
}
