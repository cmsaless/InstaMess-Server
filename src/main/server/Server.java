package main.server;

import main.services.MessagesService;
import main.services.UserService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {

    private MessagesService messagesService;
    private UserService userService;

    HashSet<ServerSocketThread> allConnections;

    private String ipAddress;
    private String port;

    private HashSet<ChatRoom> rooms;

    public Server(ApplicationConfig config) {

        messagesService = new MessagesService(this);
        userService = new UserService();

        allConnections = new HashSet<>();

        ipAddress = (String) config.get("server_ip_address");
        port = (String) config.get("server_port");
        rooms = new HashSet<>();

        try {
            openServerSocket();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void openServerSocket() throws IOException, InterruptedException {

        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port), 1, InetAddress.getByName(ipAddress));
        System.out.println("Server running...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepting new connection... " + clientSocket.getLocalSocketAddress());
            ServerSocketThread socketThread = new ServerSocketThread(this, clientSocket);
            // check if address is a pre-existing conn
            allConnections.add(socketThread);
            socketThread.start();
        }
    }

    public void recvThroughSocket(String message, ServerSocketThread source) {
        messagesService.parseMessage(message, source);
    }

    public void sendThroughSockets(String output) {
        byte[] outputArray = output.getBytes();
        for (ServerSocketThread sockTh: allConnections) {
            sockTh.send(outputArray);
        }
    }

    public ChatRoom createChatRoom(String name, String password, boolean isPrivate, User host) {
        ChatRoom newRoom = new ChatRoom(name, password, isPrivate, host);
        return newRoom;
    }

    public void addUser(String username, ServerSocketThread userSocketThread) {
        // check if blocked
        // check if
//        userService.createUser(username, userSocketThread);
    }

}
