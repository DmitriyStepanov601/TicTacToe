package modes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.ImageIcon;

import ai.BestResponse;
import ai.Board;
import ai.AI;
import ai.Move;
import graphics.Game;
import utilities.Constants;
import utilities.GameParameters;
import utilities.ResourceLoader;

/**
 * A class describing the player vs AI game mode
 *
 * @author Dmitriy Stepanov
 */
public class PlayervsAI extends XO {
    public int id;
    private final ImageIcon X;
    private final ImageIcon O;
    private AI aiPlayer;

    /**
     * Constructor - creating a new mode
     *
     * @param id       - id
     * @param aiPlayer - AI player
     * @see PlayervsAI#PlayervsAI(int, AI)
     */
    public PlayervsAI(int id, AI aiPlayer) {
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
        this.aiPlayer = aiPlayer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Game.undo.setEnabled(true);
        setIcon(X);
        List<Integer> cell = Game.getBoardCellById(id);

        if (cell != null) {
            Game.makeMove(cell.get(0), cell.get(1), Constants.X);
            if (!gameBoardMatchesWithGUIBoard()) {
                Game.undo();
                return;
            }

            if (!Game.board.isTerminal()) {
                Move aiMove;
                if (this.aiPlayer.getMaxDepth() == Constants.BEST_RESPONSE) {
                    BestResponse bestResponse = new BestResponse(Game.board.getGameBoard());
                    aiMove = bestResponse.findBestResponse();
                } else {
                    aiMove = this.aiPlayer.miniMax(Game.board);
                }

                Game.makeMove(aiMove.getRow(), aiMove.getColumn(), Constants.O);
                int aiMoveButtonId = Game.getIdByBoardCell(aiMove.getRow(), aiMove.getColumn());

                for (PlayervsAI button : Game.playervsAIS) {
                    button.aiPlayer = this.aiPlayer;
                    if (button.id == aiMoveButtonId) {
                        button.setIcon(O);
                        button.removeActionListener(button);
                    }
                }

                Board.printBoard(Game.board.getGameBoard());
                if (Game.board.isTerminal()) {
                    Game.gameOver();
                } else {
                    try {
                        this.removeActionListener(this);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                Game.gameOver();
            }
        }
    }

    private boolean gameBoardMatchesWithGUIBoard() {
        for (PlayervsAI button : Game.playervsAIS) {
            List<Integer> cell = Game.getBoardCellById(button.id);
            if (button.getIcon() == null
                    && Game.board.getGameBoard()[cell.get(0)][cell.get(1)] != Constants.EMPTY) {
                return false;
            }
        }
        return true;
    }
}