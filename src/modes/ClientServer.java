package modes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.List;
import javax.swing.ImageIcon;

import ai.Board;
import client_server.Client;
import graphics.Game;
import utilities.Constants;
import utilities.GameParameters;
import utilities.ResourceLoader;

/**
 * A class that describes the online game mode
 *
 * @author Dmitriy Stepanov
 */
public class ClientServer extends XO implements Serializable {
    public int id;
    private final ImageIcon X;
    private final ImageIcon O;
    private final String serverIP;
    private final int serverPort;
    public Client client;
    private final int playerLetter;
    public boolean programmaticallyPressed = false;

    /**
     * Constructor - creating a new mode
     *
     * @param id           - id
     * @param serverIP     - IP-address server
     * @param serverPort   - port server
     * @param playerSymbol - symbol
     * @see ClientServer#ClientServer(int, String, int, int)
     */
    public ClientServer(int id, String serverIP, int serverPort, int playerSymbol) {
        setBackground(Color.lightGray);
        setFocusable(false);
        this.id = id;
        String player1Color = String.valueOf(GameParameters.player1ColorSymbols).charAt(0)
                + String.valueOf(GameParameters.player1ColorSymbols).toLowerCase().substring(1);
        String player2Color = String.valueOf(GameParameters.player2ColorSymbols).charAt(0)
                + String.valueOf(GameParameters.player2ColorSymbols).toLowerCase().substring(1);
        this.X = new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.X, player1Color)));
        this.O = new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.O, player2Color)));
        this.addActionListener(this);
        setIcon(null);
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.client = new Client(serverIP, serverPort, playerSymbol);
        this.playerLetter = playerSymbol;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int turn = Constants.EMPTY;
        if (Game.board.getLastPlayer() == Constants.X)
            turn = Constants.O;
        else if (Game.board.getLastPlayer() == Constants.O)
            turn = Constants.X;

        if (turn != playerLetter)
            return;

        if (programmaticallyPressed) {
            turn = Game.board.getLastPlayer();
            Game.board.changeLastSymbolPlayed();
        }

        if (turn == Constants.EMPTY) {
            setIcon(null);
        } else if (turn == Constants.X) {
            setIcon(X);
        } else if (turn == Constants.O) {
            setIcon(O);
        }

        List<Integer> cell = Game.getBoardCellById(id);
        if (cell != null)
            Game.makeMove(cell.get(0), cell.get(1), turn);
        Board.printBoard(Game.board.getGameBoard());

        if (!programmaticallyPressed) {
            this.client = new Client(serverIP, serverPort, playerLetter);
            this.client.start();

            if (Game.board.isTerminal()) {
                Game.gameOver();
            }
        }

        try {
            this.removeActionListener(this);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        Game.clientServers[id] = this;
    }
}