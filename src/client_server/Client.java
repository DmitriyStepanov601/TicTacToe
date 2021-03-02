package client_server;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import ai.Move;
import graphics.Game;

/**
 * A class that describes the client model
 * @author Dmitriy Stepanov
 */
public class Client extends Thread {
	String serverIP;
	int serverPort;
	int playerSymbol;

	/**
	 * Constructor - creating a new client
	 * @see Client#Client()
	 */
	public Client() { }

	/**
	 * Constructor - creating a new client
	 * @param IP - User's IP address
	 * @param port - connection port
	 * @param playerSymbol - symbol
	 * @see Client#Client(String,int,int)
	 */
	public Client(String IP, int port, int playerSymbol) {
		this.serverIP = IP;
		this.serverPort = port;
		this.playerSymbol = playerSymbol;
	}
	
	@Override
	public void run() {
		Socket requestSocket = null;
		ObjectOutputStream out = null;
		
		try {
			requestSocket = new Socket(InetAddress.getByName(serverIP), serverPort);
			System.out.println("Client has started! Established connection with server at port: " + serverPort + "...");
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			
			Move lastMove = Game.board.getLastMove();
			out.writeObject(lastMove);
			out.flush();
			
			out.writeInt(playerSymbol);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				out.close();
				requestSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}