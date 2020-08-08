package main.java.server;

//package backend.server;
//
//import java.util.UUID;
//
//import backend.SocketThread;
//import main.java.server.Server;
//
///**
// * This class is used for taking the pieces of information parsed from the
// * initial messages and further processing them.
// *
// * @author cmsaless
// */
public class MessageFunctions {

    private Server server;

    public MessageFunctions(Server server) {
        this.server = server;
    }
}
//
//    /**
//     * If the server receives a USER message...
//     * We check if the username is valid and unique.
//     *
//     * If it does fit the criteria, we send back:
//     *     USER:APRV:username
//     * If it violates any of the rules, we send back:
//     *     USER:violation:username
//     *
//     * @param senderID
//     * @param username
//     */
//    public void UserMessage(SocketThread senderSockTh, String username) {
//
//        String response = "USER:";
//
//        if (!(MessagesUtils.isUsernameShortEnough(username))) {
//            response += "LENL:";
//        } else if (!(MessagesUtils.isUsernameLongEnough(username))) {
//            response += "LENS:";
//        } else if (!(MessagesUtils.isUsernameValid(username))) {
//            response += "VALD:";
//        } else if (!(MessagesUtils.isUsernameUnique(username, _server.getSetOfUsers()))) {
//            response += "UNIQ:";
//        } else {
//            _server.createNewUser(username, senderSockTh);
//            response += "APRV:";
//        }
//
//        response += username;
//        _server.sendThroughSocket(response, senderSockTh);
//    }
//
//    /**
//     * If the server receives a HOST message...
//     *
//     * If the sending user is eligible to host a room we send back:
//     *     HOST:APRV:join code:sender name
//     * If the sending user is already in a chat, we send back:
//     *     HOST:FAIL
//     */
//    public void HostMessage(User sender) {
//
//        SocketThread senderSockTh = sender.getSocketThread();
//        ChatRoom senderRoom = _server.findChatRoomWithUser(sender);
//
//        String response = "HOST:";
//
//        if (senderRoom == null) {
//            String generatedCode = MessagesUtils.generateJoinCode();
//            _server.createNewChatRoom(sender, generatedCode);
//            response += "APRV:" + generatedCode + ":" + sender.getUsername();
//        } else {
//            response += "FAIL";
//        }
//
//        _server.sendThroughSocket(response, senderSockTh);
//    }
//
//    /**
//     * If the server receives a JOIN message...
//     * We use the join code and chat ID to attempt to join the room. If it's
//     * successful, we add the user, notify all other users in the chat, and
//     * then send a response to the joiner.
//     *
//     * If the join is successful, we send back:
//     *     JOIN:APRV:join code:hostname
//     * If the join was unsuccessful, we send back:
//     *     JOIN:FAIL:join code:hostname
//     */
//    public void JoinMessage(User sender, String joinCode, String chatIDStr) {
//
//        SocketThread senderSockTh = sender.getSocketThread();
//
//        String response = "JOIN:";
//
//        UUID chatID = UUID.fromString(chatIDStr);
//        ChatRoom room = _server.findChatRoomWithID(chatID);
//
//        String hostname = "";
//        boolean success = false;
//        if (room != null) {
//            if (room.getJoinCode().equals(joinCode)) {
//                _server.addUserToChatRoom(sender, room);
//                hostname = room.getHost().getUsername();
//                success = true;
//            }
//        }
//
//        String status = success ? "APRV:" : "FAIL:";
//
//        response += status + joinCode + ":" + hostname;
//        _server.sendThroughSocket(response, senderSockTh);
//    }
//
//    /**
//     * If the server receives a LIST message...
//     * First, we check what type of search it is. It's either for rooms or for
//     * users in a room. We then iterate over the corresponding set of items and
//     * then add whatever ones are applicable to the search results.
//     *
//     * If the search is for rooms, we send back:
//     *     LIST:ROOMS:room host:room id:room host:room id:etc.
//     * If the search is for users in a room, we send back:
//     *     LIST:USERS:username:user id:username:user id:etc.
//     */
//    public void ListMessage(User sender, String typeOfSearch, String query) {
//
//        SocketThread senderSockTh = sender.getSocketThread();
//        ChatRoom senderRoom = _server.findChatRoomWithUser(sender);
//
//        String response = "LIST:" + typeOfSearch + ":";
//
//        boolean kicking = typeOfSearch.equals("KICKS");
//
//        if (typeOfSearch.equals("ROOMS")) {
//            for (ChatRoom room : _server.getSetOfChatRooms()) {
//                String roomHostname = room.getHost().getUsername();
//                if (roomHostname.contains(query)) {
//                    response += roomHostname + ":" + room.getID() + ":";
//                }
//            }
//        } else if (typeOfSearch.equals("USERS") || kicking) {
//            for (User user : senderRoom.getUsers()) {
//                String username = user.getUsername();
//                if (username.contains(query)) {
//                    if (kicking && !user.getID().equals(sender.getID())) {
//                        response += username + ":" + user.getID() + ":";
//                    }
//                }
//            }
//        }
//
//        _server.sendThroughSocket(response, senderSockTh);
//    }
//
//    /**
//     * If the server receives a MESG message...
//     * We find the chat room the sender belongs to format the message with HTML
//     * HTML-formatted and send it to all users in the chat (including the
//     * sender).
//     *
//     * To all users in the chat:
//     *     MESG:formatted message
//     */
//    public void ChatMessage(User sender, String messageText) {
//
//        ChatRoom senderRoom = _server.findChatRoomWithUser(sender);
//
//        final String msgColorBegin = "<font color=\"green\">";
//        final String msgColorEnd = "</font>";
//
//        String formattedMessage = "MESG:";
//
//        formattedMessage += msgColorBegin;
//        formattedMessage += "[" + sender.getUsername() + " (" + MessagesUtils.getTime() + ")] ";
//        formattedMessage += msgColorEnd;
//        formattedMessage += messageText;
//
//        if (senderRoom != null) {
//            for (User user : senderRoom.getUsers()) {
//                _server.sendThroughSocket(formattedMessage, user.getSocketThread());
//            }
//        }
//    }
//
//    /**
//     * If the server receives a NOTF message...
//     * We use the color to format the notification, and then use the chat ID to
//     * find which chat this notification is being sent to.
//     *
//     * To all users in the chat room with chatID:
//     *     MESG:formatted notification
//     */
//    public void NotificationMessage(String chatID, String color, String notificationText) {
//
//        final String notfColorBegin = "<font color=\"" + color + "\">";
//        final String notfColorEnd = "</font>";
//
//        String formattedNotification = "MESG:";
//
//        formattedNotification += notfColorBegin;
//        formattedNotification += ">>> " + notificationText;
//        formattedNotification += notfColorEnd;
//
//        ChatRoom room = _server.findChatRoomWithID(UUID.fromString(chatID));
//
//        if (room != null) {
//            for (User user : room.getUsers()) {
//                _server.sendThroughSocket(formattedNotification, user.getSocketThread());
//            }
//        }
//    }
//
//    /**
//     * If the server receives a CODE message...
//     * We first check if the sender is actually the host of their chat and then
//     * we generate a new code for the chat and send it to all the users in the
//     * chat.
//     *
//     * We send this message to all users in the chat:
//     *     CODE:new join code
//     */
//    public void CodeMessage(User sender) {
//
//        ChatRoom senderRoom = _server.findChatRoomWithUser(sender);
//
//        if (senderRoom.isHost(sender)) {
//
//            String generatedCode = MessagesUtils.generateJoinCode();
//            senderRoom.setNewCode(generatedCode);
//
//            String newCodeMsg = "CODE:" + generatedCode;
//
//            for (User user : senderRoom.getUsers()) {
//                _server.sendThroughSocket(newCodeMsg, user.getSocketThread());
//            }
//        }
//    }
//
//    /**
//     * If the server receives a KICK message...
//     * First, check to make sure the user making the kick request is the host
//     * of their chat. If they are, remove the user and send a KICK message to
//     * them. Everyone else in the chat should be sent a notification.
//     *
//     * Send to kickee:
//     *     KICK
//     */
//    public void KickMessage(User sender, String kickeeIDStr) {
//
//        ChatRoom senderRoom = _server.findChatRoomWithUser(sender);
//
//        UUID kickeeID = UUID.fromString(kickeeIDStr);
//        User kickee = _server.findUserWithID(kickeeID);
//
//        if (senderRoom.isHost(sender)) {
//            if (kickee != null && senderRoom.getUsers().contains(kickee) && kickee.getID() != sender.getID()) {
//                ChatRoom room = _server.findChatRoomWithUser(sender);
//                if (room != null) {
//                    _server.removeUserFromChatRoom(kickee);
//                    _server.sendThroughSocket("KICK", kickee.getSocketThread());
//
//                    String notificationText = kickee.getUsername() + " has been kicked from the chat";
//                    NotificationMessage(senderRoom.getID().toString(), "red", notificationText);
//                }
//            }
//        }
//
//    }
//
//    /**
//     * If the server receives a LEAV message...
//     * Then a client has disconnected. This message can be sent from when a
//     * client manually disconnects or when a client's connection randomly
//     * closes. We remove the leaver from their chat and the server's data
//     * structures. We then notify all the other users in that chat.
//     *
//     * @param leaverID - UUID of the user who disconnected
//     */
//    public void LeaveMessage(UUID leaverID) {
//
//        User leaver = _server.findUserWithID(leaverID);
//        ChatRoom senderRoom = _server.findChatRoomWithUser(leaver);
//
//        _server.removeUserFromChatRoom(leaver);
//
//        String notificationText = leaver.getUsername() + " has left the chat";
//        NotificationMessage(senderRoom.getID().toString(), "red", notificationText);
//    }
//
//    /**
//     * If the server receives a TERM message...
//     * That means the host of the chat themselves has disconnected which means
//     * the chat room must be ended and all the other users should be notified.
//     *
//     * @param hostID - UUID of host
//     */
//    public void TerminateMessage(UUID hostID) {
//
//        User host = _server.findUserWithID(hostID);
//        ChatRoom hostRoom = _server.findChatRoomWithUser(host);
//
//        for (User u : hostRoom.getUsers()) {
//            _server.sendThroughSocket("TERM", u.getSocketThread());
//        }
//    }
//}
