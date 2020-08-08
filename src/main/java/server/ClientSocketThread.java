package main.java.server;

import java.io.*;
import java.net.Socket;

/**
 * This class uses threading to be able to send messages through the socket
 * while at the same time constantly checking for new inputs.
 * 
 * @author cmsaless
 */
public class ClientSocketThread extends Thread {

	private Server server;
	private Socket socket;

	private DataOutputStream outStream;

	/**
	 * Initialize the SocketThread with the passed host, the socket it's
	 * reading and writing from and to and the ID of the socket. Also create
	 * the Reader and Writer using the I/O streams of the socket.
	 *
	 * @param socket - the socket it's reading/writing
	 */
	public ClientSocketThread(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}


	/*
	 * When the thread is started, it will constantly read from the input
	 * stream of the socket. 
	 * 
	 * It waits a quarter of a second after each read to lower overhead. 
	 */
	public void run() {

		BufferedReader inStream = null;
		try {
			inStream = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
			outStream = new DataOutputStream(socket.getOutputStream());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			while (true) {

				String inputLine;
				while ((inputLine = inStream.readLine()) != null) {
					System.out.println(inputLine);
					if (inputLine.equals("q")) {
						break;
					}
				}

//				server.recvThroughSocket(input);
			}
		} catch (IOException ex) {
			return;
		}

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


}
