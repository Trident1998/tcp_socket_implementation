# Overview

The networking program I've written is a basic TCP client-server chat application where the server manages client connections and facilitates real-time message exchange. Here's how the software works and what it aims to accomplish:

**TcpServer:**

- The server listens for incoming client connections on port 8080.
- Each client is handled by a separate thread (ClientHandler), allowing multiple clients to connect simultaneously.
- The server maintains a list of active users and broadcasts messages to all connected clients.
- When a client connects, the server prompts them for a username, and the username is then broadcast to all connected clients. The server also handles disconnections by removing clients when they disconnect.

**TcpClientGUI:**

- The client application is a GUI-based chat program built using Java Swing.
- Upon launch, the user is prompted to enter a username. This username is then sent to the server to register the user.
- The user can type messages in the input field. When they press Enter, the message is sent to the server.
- The GUI displays incoming messages in a scrollable text area, ensuring a smooth experience when multiple messages are sent in quick succession.
- The client connects to the server at localhost on port 8080 and listens for messages, updating the display when new messages arrive.

The purpose of writing this software is to build a simple, functional client-server chat application that will deepen my understanding of networking concepts such as sockets, multi-threading, and message broadcasting. It also aims to familiarize me with handling concurrent connections on the server side and ensuring that messages are efficiently transmitted between clients and the server in real-time.

By implementing this, I aim to improve my proficiency with network programming in Java and gain more experience with using GUI frameworks for creating interactive user interfaces.

[Software Demo Video](https://youtu.be/-Z0OnCAWego)

# Network Communication

I implemented a client-server architecture for this application. The server acts as a central hub that listens for incoming client connections and broadcasts messages to all connected clients. Each client communicates with the server, sending messages and receiving updates in real-time. This centralized approach simplifies message management and allows the server to maintain control over active users and ensure smooth communication between clients.

**Protocol and Ports:**
1. **Protocol**: I am using TCP (Transmission Control Protocol) for communication between the client and server. TCP ensures reliable, ordered, and error-checked delivery of messages between the server and its clients, which is crucial for a chat application.
2. **Port**: The server listens for incoming connections on port 8080. Clients connect to the server using this port.

**Message Format:**
The format of the messages being sent between the client and server is **plain text**. However, the server structures the messages in a way that can convey both normal chat messages and special system messages, such as the list of active users.

**Message Flow:**
1. **Client to Server:** A client sends a message by typing it into the input field. The message is sent to the server using out.println(message). The message could be a normal chat message or a special command if necessary. 
2. **Server to Client:** When the server receives a message, it sends it to all connected clients. The server broadcasts the message using the broadcastMessage() method, which sends the message to each ClientHandler. The client listens for these messages in a separate thread and displays them in the GUI.

# Development Environment

## Tools Used:
- **IDE**: IntelliJ IDEA 
- **Version Control**: Git

## Programming Language and Libraries:
- **Language**: Java
- **Libraries**:
- - **Java Networking API** (java.net.*): For implementing socket communication.
- - **Java Swing** (javax.swing.*): For creating the client’s GUI.

# Useful Websites

{Make a list of websites that you found helpful in this project}
* [Transmission Control Protocol](https://en.wikipedia.org/wiki/Transmission_Control_Protocol)
* [GeeksforGeeks](https://www.geeksforgeeks.org/what-is-transmission-control-protocol-tcp/)
* [Network Lessons](https://networklessons.com/tag/tcp)
* [YouTube](https://www.youtube.com/watch?v=JFch3ctY6nE&ab_channel=PracticalNetworking)

# Future Work

1. **Error Handling**: Improve error messages and add specific exception handling for different failure scenarios (e.g., connection timeouts, client disconnections).
2. **User Authentication**: Implement a simple user authentication system to allow clients to log in with a password before joining the chat.
3. **Private Messaging**: Add functionality for private messages between clients, allowing direct communication without broadcasting to everyone.
4. **Message Formatting**: Enhance message formatting, such as adding timestamps or distinguishing between user and server messages.
5. **GUI Enhancements**: Improve the user interface with features like message notifications, emoticons, and better message layout.
6. **Persistence**: Add the ability to save chat history on the server or locally on clients for later retrieval.