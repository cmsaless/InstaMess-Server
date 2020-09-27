package main.services;

import main.server.User;

import java.net.InetAddress;
import java.util.HashSet;

public class UserService {

//    private HashSet<User> users;
//    private HashSet<InetAddress> blockedClients;
//    private HashSet<InetAddress> currentConnections;
//
//    public UserService() {
//        users = new HashSet<>();
//        blockedClients = new HashSet<>();
//        currentConnections = new HashSet<>();
//    }
//
//    public void createUser(String username, SocketThread userSocketThreadDUD) {
//        User newUser = new User(username, userSocketThreadDUD);
//        removeAnyCurrentConnections(userSocketThreadDUD);
//        currentConnections.add(userSocketThreadDUD.getSocket().getInetAddress());
//        users.add(newUser);
//    }
//
//    public boolean usernameIsTaken(String username) {
//        for (User user : users) {
//            if (user.getName().equals(username)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean clientIsBlocked(SocketThread clientSocketThreadDUD) {
//        return blockedClients.contains(clientSocketThreadDUD.getSocket().getInetAddress());
//    }
//
//    private void removeAnyCurrentConnections(SocketThread clientSocketThreadDUD) {
//        InetAddress newAddress = clientSocketThreadDUD.getSocket().getInetAddress();
//        if (currentConnections.contains(newAddress)) {
//            for (User user: users) {
//                InetAddress userAddress = user.getSocketThreadDUD().getSocket().getInetAddress();
//                if (userAddress.equals(newAddress)) {
//                    users.remove(user);
//                    currentConnections.remove(userAddress);
//                    return;
//                }
//            }
//        }
//    }

}
