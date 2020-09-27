package main.driver;

import main.server.Server;
import main.services.MessagesService;

public class Driver {
    public static void main(String[] args) {

        final ApplicationConfig appProperties = new ApplicationConfig();
        final MessagesService messagesService = new MessagesService();

        new Server(messagesService, appProperties);
    }
}
