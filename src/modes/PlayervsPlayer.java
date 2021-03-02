package modes;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.ImageIcon;
import ai.Board;
import graphics.Game;
import utilities.Constants;
import utilities.GameParameters;
import utilities.ResourceLoader;

/**
 * A class describing the player versus player game mode
 * @author Dmitriy Stepanov
 */
public class PlayervsPlayer extends XO {
	public int id;
	private final ImageIcon X;
	private final ImageIcon O;

	/**
	 * Constructor - creating a new mode
	 * @param id - id
	 * @see PlayervsPlayer#PlayervsPlayer(int)
	 */
	public PlayervsPlayer(int id) {
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Game.undo.setEnabled(true);
		int turn = Constants.EMPTY;
		if (Game.board.getLastPlayer() == Constants.X)
			turn = Constants.O;
		else if (Game.board.getLastPlayer() == Constants.O)
			turn = Constants.X;
		
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
		
		if (Game.board.isTerminal())
			Game.gameOver();
		
		try {
			this.removeActionListener(this);
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}
}