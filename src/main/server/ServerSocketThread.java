package main.server;

import main.socketthread.SocketThread;

import java.io.*;
import java.net.Socket;

public class ServerSocketThread extends SocketThread {

    private Server server;

    /**
     * Initialize the SocketThread with the passed host, the socket it's
     * reading and writing from and to and the ID of the socket. Also create
     * the Reader and Writer using the I/O streams of the socket.
     *
     * @param socket - the socket it's reading/writing
     */
    public ServerSocketThread(Server server, Socket socket) {
        super(socket);
        this.server = server;
    }

    /*
     * When the thread is started, it will constantly read from the input
     * stream of the socket.
     *
     * It waits a quarter of a second after each read to lower overhead.
     */

    public void run() {
        try {
            while (true) {
                String line = super.inStream.readUTF();
                recv(line);
            }
        } catch (IOException ex) {
            return;
        }
    }

    @Override
    public void recv(String input) {
        server.recvThroughSocket(input, this);
    }

    /**
     * When the client/server has something to send, send it using the println
     * function in the PrintWriter.
     *
     * NOTE: We use println instead of print, because the '\n' character
     * signifies the end of a line (input). If we use print, the destination
     * will block forever until it sees the '\n' character.
     *
     * @param output - text to send
     */
    public void send(byte[] output) {
        try {
            outStream.writeInt(output.length);
            outStream.write(output);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Socket getSocket() {
        return super.getSocket();
    }
}
