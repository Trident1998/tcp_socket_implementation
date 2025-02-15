package org.example;

import java.io.*;
import java.net.*;
import java.util.*;

public class TcpServer {

    private static final int PORT = 8080;
    private final Set<String> activeUsers = new HashSet<>();
    private final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        new TcpServer().start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("TCP Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String username) {
        activeUsers.add(username);
        broadcastActiveUsers();
    }

    public void removeUser(String username) {
        activeUsers.remove(username);
        broadcastActiveUsers();
    }

    public void broadcastMessage(Message message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void broadcastActiveUsers() {
        broadcastMessage(new Message("SERVER", "ACTIVE_USERS:" + String.join(",", activeUsers)));
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}