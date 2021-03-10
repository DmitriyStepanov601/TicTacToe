package ai;

import java.util.ArrayList;
import java.util.Random;

import utilities.Constants;

/**
 * A class that describes the mechanism of AI operation based on the minimax algorithm
 *
 * @author Dmitriy Stepanov
 */
public class AI {
    private int maxDepth;
    private int playerLetter;

    /**
     * Constructor - creating a new AI
     *
     * @see AI#AI()
     */
    public AI() {
        maxDepth = 3;
        playerLetter = Constants.O;
    }

    /**
     * Constructor - creating a new AI
     *
     * @param maxDepth     - the level of intelligence
     * @param playerLetter - players symbol
     * @see AI#AI(int, int)
     */
    public AI(int maxDepth, int playerLetter) {
        this.maxDepth = maxDepth;
        this.playerLetter = playerLetter;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getPlayerSymbol() {
        return playerLetter;
    }

    public void setPlayerSymbol(int playerSymbol) {
        this.playerLetter = playerSymbol;
    }

    public Move miniMax(Board board) {
        if (playerLetter == Constants.X) {
            return max(new Board(board), 0);
        } else {
            return min(new Board(board), 0);
        }
    }

    public Move max(Board board, int depth) {
        Random r = new Random();
        if ((board.isTerminal()) || (depth == maxDepth)) {
            int value = board.evaluate();
            return new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), value);
        }

        ArrayList<Board> children = new ArrayList<>(board.getChildren(Constants.X));
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (Board child : children) {
            Move move = min(child, depth + 1);
            if (move.getValue() >= maxMove.getValue()) {
                if ((move.getValue() == maxMove.getValue())) {
                    if (r.nextInt(2) == 0) {
                        maxMove.setRow(child.getLastMove().getRow());
                        maxMove.setColumn(child.getLastMove().getColumn());
                        maxMove.setValue(move.getValue());
                    }
                } else {
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setColumn(child.getLastMove().getColumn());
                    maxMove.setValue(move.getValue());
                }
            }
        }
        return maxMove;
    }

    public Move min(Board board, int depth) {
        Random r = new Random();

        if ((board.isTerminal()) || (depth == maxDepth)) {
            int value = board.evaluate();
            return new Move(board.getLastMove().getRow(), board.getLastMove().getColumn(), value);
        }

        ArrayList<Board> children = new ArrayList<>(board.getChildren(Constants.O));
        Move minMove = new Move(Integer.MAX_VALUE);
        for (Board child : children) {
            Move move = max(child, depth + 1);
            if (move.getValue() <= minMove.getValue()) {
                if ((move.getValue() == minMove.getValue())) {
                    if (r.nextInt(2) == 0) {
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setColumn(child.getLastMove().getColumn());
                        minMove.setValue(move.getValue());
                    }
                } else {
                    minMove.setRow(child.getLastMove().getRow());
                    minMove.setColumn(child.getLastMove().getColumn());
                    minMove.setValue(move.getValue());
                }
            }
        }
        return minMove;
    }
}