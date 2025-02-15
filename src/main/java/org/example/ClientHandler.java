package org.example;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final TcpServer server;
    private String username;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket, TcpServer server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read username
            username = in.readLine();
            server.addUser(username);

            // Broadcast active users
            server.broadcastActiveUsers();

            // Handle messages
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.printf("Received message from %s: %s\n", username, inputLine);
                server.broadcastMessage(new Message(username, inputLine));
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + username);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            server.removeUser(username);
            server.removeClient(this);
        }
    }

    public void sendMessage(Message message) {
        out.println(message.user() + ": " + message.message());
    }
}