package main.services;

import main.server.ServerSocketThread;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Date;

public class MessagesService {

    public MessagesService() {

    }

    public JSONObject parseMessage(String message, ServerSocketThread source) {

        JSONObject jsonMessage = convertMessageToJson(message);

        String messageType = (String) jsonMessage.get("type");
        messageType = messageType.toUpperCase();

        JSONObject jsonResponse = new JSONObject();

        switch (messageType) {
            case "NEW USER": {
                jsonResponse = handleNewUserMessage(jsonMessage);
            }
            break;
            case "MESSAGE": {
                jsonResponse = handleMessage(jsonMessage);
            }
            break;
            case "EXIT": {
                jsonResponse = handleExitMessage(jsonMessage);
            }
        }

        return jsonResponse;
    }

    private JSONObject handleNewUserMessage(JSONObject jsonMessage) {

        JSONObject jsonResponse = new JSONObject();
        String newUsername = (String) jsonMessage.get("username");
        jsonResponse.put("content", "Welcome, " + newUsername);

        return jsonResponse;
    }

    private JSONObject handleMessage(JSONObject jsonMessage) {

        JSONObject jsonResponse = new JSONObject();

        String username = (String) jsonMessage.get("user");
        String msgContent = (String) jsonMessage.get("content");
        String content = "[" + new Date().toString() + "] " + username + ": " + msgContent;
        jsonResponse.put("content", content);

        return jsonResponse;
    }

    private JSONObject handleExitMessage(JSONObject jsonMessage) {

        JSONObject jsonResponse = new JSONObject();

        String username = (String) jsonMessage.get("user");
        String content = username + " has left";
        jsonResponse.put("content", content);

        return jsonResponse;
    }

    private JSONObject convertMessageToJson(String message) {

        JSONParser parser = new JSONParser();
        JSONObject json = null;

        try {
            json = (JSONObject) parser.parse(message);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return json;
    }
}
