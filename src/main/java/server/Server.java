package main.java.server;

import main.java.config.ApplicationProperties;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {

    private String ipAddress;
    private String port;

    private HashSet<ChatRoom> rooms;
    private HashSet<User> users;

    public Server(ApplicationProperties applicationProperties) {

        ipAddress = (String) applicationProperties.get("server_ip_address");
        port = (String) applicationProperties.get("server_port");
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
            System.out.println("Accepting new connection...");
            ClientSocketThread clientSocketThread = new ClientSocketThread(this, clientSocket);
            clientSocketThread.start();
        }
    }

    public void recvThroughSocket(byte[] input) {
        System.out.println("here: "+input);
        MessageParser.parseMessage(new String(input));
    }

    public void sendThroughSocket(String output, ClientSocketThread clientSocketThread) {
        byte[] outputArray = output.getBytes();
        clientSocketThread.send(outputArray);
    }

    public ChatRoom createChatRoom(String name, String password, boolean isPrivate, User host) {
        ChatRoom newRoom = new ChatRoom(name, password, isPrivate, host);
        return newRoom;
    }

}
