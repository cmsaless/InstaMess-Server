package main.driver;

import main.server.ApplicationConfig;
import main.server.Server;

public class Driver {
    public static void main(String[] args) {
        final ApplicationConfig appProperties = new ApplicationConfig();
        new Server(appProperties);
    }
}
