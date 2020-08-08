package main.java.server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MessageParser {

    public MessageParser() { }

    public static void parseMessage(String message) {

        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(message);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String messageType = (String) json.get("type");
        messageType = messageType.toUpperCase();

        System.out.println(message);

        switch (messageType) {
            case "NEW USER": {
                System.out.println("This message is a new user message");
            }
            break;
            case "NEW CHAT": {

            }
            break;
            case "LIST": {

            }
            break;
            case "JOIN": {

            }
            break;
            case "MESG": {

            }
            break;
            case "NOTF": {

            }
            break;
            case "CODE": {

            }
            break;
            case "KICK": {

            }
            break;
            case "LEAV": {

            }
            break;
        }

    }

}
