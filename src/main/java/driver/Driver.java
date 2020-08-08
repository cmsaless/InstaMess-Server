package main.java.driver;

import main.java.config.ApplicationProperties;
import main.java.server.Server;

public class Driver {

    public static void main(String[] args) {
        final ApplicationProperties appProperties = new ApplicationProperties();
        new Server(appProperties);
    }

}
