package main.server;

import main.driver.ApplicationConfig;
import main.services.MessagesService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {

    private MessagesService messagesService;
    private HashSet<ServerSocketThread> allConnections;

    private String ipAddress;
    private String port;

    public Server(MessagesService messagesService, ApplicationConfig config) {

        this.messagesService = messagesService;
        allConnections = new HashSet<>();

        ipAddress = (String) config.get("server_ip_address");
        port = (String) config.get("server_port");

        try {
            openServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openServerSocket() throws IOException {

        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port), 1, InetAddress.getByName(ipAddress));
        System.out.println("Server running...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepting new connection... " + clientSocket.getLocalSocketAddress());
            ServerSocketThread socketThread = new ServerSocketThread(this, clientSocket);
//            overwriteExistingConnections(socketThread);
            allConnections.add(socketThread);
            socketThread.start();
        }
    }

    public void recvThroughSocket(String message, ServerSocketThread source) {
        String response = messagesService.parseMessage(message, source).toJSONString();
        sendThroughSockets(response);
    }

    public void sendThroughSockets(String response) {
        for (ServerSocketThread sockTh: allConnections) {
            sockTh.send(response);
        }
    }

    private void overwriteExistingConnections(ServerSocketThread newSocketThread) {
        InetAddress newAddress = newSocketThread.getSocket().getInetAddress();
        for (ServerSocketThread servSockTh: allConnections) {
            InetAddress existingAddress = servSockTh.getSocket().getInetAddress();
            if (existingAddress.equals(newAddress)) {
                allConnections.remove(servSockTh);
                return;
            }
        }
    }

}
