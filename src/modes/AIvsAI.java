package modes;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

import ai.Board;
import graphics.Game;
import utilities.Constants;
import utilities.GameParameters;
import utilities.ResourceLoader;

/**
 * A class describing the AI vs AI game mode
 *
 * @author Dmitriy Stepanov
 */
public class AIvsAI extends XO {
    public int id;
    private final ImageIcon X;
    private final ImageIcon O;
    public int player;

    /**
     * Constructor - creating a new mode
     *
     * @param id - id
     * @see AIvsAI#AIvsAI(int)
     */
    public AIvsAI(int id) {
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
        this.player = Constants.EMPTY;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (player == Constants.X)
            setIcon(X);
        else if (player == Constants.O)
            setIcon(O);

        Board.printBoard(Game.board.getGameBoard());
        removeActionListener(this);
    }
}