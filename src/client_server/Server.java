package client_server;
import ai.Move;
import modes.ClientServer;
import graphics.Game;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A class that describes the server model
 * @author Dmitriy Stepanov
 */
public class Server extends Thread {
	int serverPort;

	/**
	 * Constructor - creating a new server
	 * @see Server#Server()
	 */
	public Server() { }

	/**
	 * Constructor - creating a new server
	 * @param port - connection port
	 * @see Server#Server(int)
	 */
	public Server(int port) {
		this.serverPort = port;
	}
	
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		Socket connection = null;
		ObjectInputStream in = null;
		try {
			System.out.println("Server thread with id " + this.getId() + ": Server listening at port: " + serverPort + "...");
			serverSocket = new ServerSocket(serverPort);

			do {
				connection = serverSocket.accept();
				System.out.println("Server accepted connection!");
				in = new ObjectInputStream(connection.getInputStream());

				Move lastMove = (Move) in.readObject();
				int id = Game.getIdByBoardCell(lastMove.getRow(), lastMove.getColumn());
				int opposingPlayerSymbol = in.readInt();
				Game.board.setLastPlayer(opposingPlayerSymbol);

				for (ClientServer button : Game.clientServers) {
					if (button.id == id) {
						button.programmaticallyPressed = true;
						button.doClick();
						button.programmaticallyPressed = false;
					}
				}

			} while (!Game.board.isTerminal());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		finally {
			try {
				in.close();
				connection.close();
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Game.gameOver();
	}
}