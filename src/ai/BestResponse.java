package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utilities.Constants;

/**
 * A class that describes the best strategy for the game
 *
 * @author Dmitriy Stepanov
 */
public class BestResponse {
    private int[][] givenBoard;
    private Move bestResponse;

    /**
     * Constructor - creating a new best strategy
     *
     * @see BestResponse#BestResponse()
     */
    public BestResponse() {
        this.givenBoard = new int[3][3];
    }

    /**
     * Constructor - creating a new best strategy
     *
     * @param givenBoard - the resulting playing field
     * @see BestResponse#BestResponse(int[][])
     */
    public BestResponse(int[][] givenBoard) {
        this.givenBoard = givenBoard;
    }

    public int[][] getGivenBoard() {
        return givenBoard;
    }

    public void setGivenBoard(int[][] givenBoard) {
        this.givenBoard = givenBoard;
    }

    public Move getBestResponse() {
        return bestResponse;
    }

    public void setBestResponse(Move bestResponse) {
        this.bestResponse = bestResponse;
    }

    public Move findBestResponse() {
        int number_of_empty_cells = Board.getNumberOfEmptyCells(this.givenBoard);
        if (number_of_empty_cells == 0)
            return null;

        Random r = new Random();
        if (Board.isGameBoardEmpty(this.givenBoard)) {
            return this.bestResponse = new Move(1, 1, Constants.X);
        }

        if (((this.givenBoard[0][0] == Constants.X || this.givenBoard[0][1] == Constants.X
                || this.givenBoard[0][2] == Constants.X || this.givenBoard[1][0] == Constants.X
                || this.givenBoard[1][2] == Constants.X || this.givenBoard[2][0] == Constants.X
                || this.givenBoard[2][1] == Constants.X || this.givenBoard[2][2] == Constants.X))
                && this.givenBoard[1][1] == Constants.EMPTY && number_of_empty_cells == 8) {
            return this.bestResponse = new Move(1, 1, Constants.O);
        }

        if (this.givenBoard[1][1] == Constants.X && number_of_empty_cells == 8) {
            int random_number = r.nextInt(4) + 1;
            if (random_number == 1)
                return this.bestResponse = new Move(0, 0, Constants.O);
            if (random_number == 2)
                return this.bestResponse = new Move(0, 2, Constants.O);
            if (random_number == 3)
                return this.bestResponse = new Move(2, 0, Constants.O);
            return this.bestResponse = new Move(2, 2, Constants.O);
        }

        if (((this.givenBoard[0][1] == Constants.O && this.givenBoard[1][1] == Constants.X)
                || (this.givenBoard[1][0] == Constants.O && this.givenBoard[1][1] == Constants.X)
                || (this.givenBoard[1][2] == Constants.O && this.givenBoard[1][1] == Constants.X)
                || (this.givenBoard[2][1] == Constants.O && this.givenBoard[1][1] == Constants.X))
                && number_of_empty_cells == 7) {
            int random_number = r.nextInt(4) + 1;
            if (random_number == 1)
                return this.bestResponse = new Move(0, 0, Constants.X);
            if (random_number == 2)
                return this.bestResponse = new Move(0, 2, Constants.X);
            if (random_number == 3)
                return this.bestResponse = new Move(2, 0, Constants.X);
            return this.bestResponse = new Move(2, 2, Constants.X);
        }

        if ((this.givenBoard[0][0] == Constants.O && this.givenBoard[1][1] == Constants.X)
                && number_of_empty_cells == 7) {
            int random_number = r.nextInt(3) + 1;
            if (random_number == 1)
                return this.bestResponse = new Move(0, 2, Constants.X);
            if (random_number == 2)
                return this.bestResponse = new Move(2, 0, Constants.X);
            return this.bestResponse = new Move(2, 2, Constants.X);
        }

        if ((this.givenBoard[0][2] == Constants.O && this.givenBoard[1][1] == Constants.X)
                && number_of_empty_cells == 7) {
            int random_number = r.nextInt(3) + 1;
            if (random_number == 1)
                return this.bestResponse = new Move(0, 0, Constants.X);
            if (random_number == 2)
                return this.bestResponse = new Move(2, 0, Constants.X);
            return this.bestResponse = new Move(2, 2, Constants.X);
        }

        if ((this.givenBoard[2][0] == Constants.O && this.givenBoard[1][1] == Constants.X)
                && number_of_empty_cells == 7) {
            int random_number = r.nextInt(3) + 1;
            if (random_number == 1)
                return this.bestResponse = new Move(0, 0, Constants.X);
            if (random_number == 2)
                return this.bestResponse = new Move(0, 2, Constants.X);
            return this.bestResponse = new Move(2, 2, Constants.X);
        }

        if ((this.givenBoard[2][2] == Constants.O && this.givenBoard[1][1] == Constants.X)
                && number_of_empty_cells == 7) {
            int random_number = r.nextInt(3) + 1;
            if (random_number == 1)
                return this.bestResponse = new Move(0, 0, Constants.X);
            if (random_number == 2)
                return this.bestResponse = new Move(0, 2, Constants.X);
            return this.bestResponse = new Move(2, 0, Constants.X);
        }

        Move br = casesThatSuggestWinOrNoLose(number_of_empty_cells, 6);
        if (br != null) return br;
        if (((this.givenBoard[0][0] == Constants.X && this.givenBoard[1][1] == Constants.X
                && this.givenBoard[2][2] == Constants.O)
                ||
                (this.givenBoard[2][2] == Constants.X && this.givenBoard[1][1] == Constants.X
                        && this.givenBoard[0][0] == Constants.O))
                && number_of_empty_cells == 6) {
            int random_number = r.nextInt(2) + 1;
            if (random_number == 1)
                return this.bestResponse = new Move(0, 2, Constants.O);
            return this.bestResponse = new Move(2, 0, Constants.O);
        }

        if ((this.givenBoard[0][1] == Constants.X && this.givenBoard[1][1] == Constants.X
                && this.givenBoard[2][0] == Constants.O)
                && number_of_empty_cells == 6) {
            int random_number = r.nextInt(2) + 1;
            if (random_number == 1)
                return this.bestResponse = new Move(2, 0, Constants.O);
            return this.bestResponse = new Move(2, 2, Constants.O);
        }

        if ((this.givenBoard[1][0] == Constants.X && this.givenBoard[1][1] == Constants.X
                && this.givenBoard[1][2] == Constants.O)
                && number_of_empty_cells == 6) {
            int random_number = r.nextInt(2) + 1;
            if (random_number == 1)
                return this.bestResponse = new Move(0, 2, Constants.O);
            return this.bestResponse = new Move(2, 2, Constants.O);
        } else {
            if ((this.givenBoard[0][2] == Constants.X && this.givenBoard[1][1] == Constants.X
            && this.givenBoard[2][0] == Constants.O
            ||
            (this.givenBoard[2][0] == Constants.X && this.givenBoard[1][1] == Constants.X
                    && this.givenBoard[0][2] == Constants.O))
            && number_of_empty_cells == 6) {
        int random_number = r.nextInt(2) + 1;
        if (random_number == 1)
            return this.bestResponse = new Move(0, 0, Constants.O);
                return this.bestResponse = new Move(2, 2, Constants.O);
    }

            br = casesThatSuggestWinOrNoLose(number_of_empty_cells, 5);
    if (br != null) return br;
    if ((this.givenBoard[0][1] == Constants.X && this.givenBoard[1][0] == Constants.X
            && this.givenBoard[1][2] == Constants.O && this.givenBoard[2][1] == Constants.O)
            && number_of_empty_cells == 5) {
        return this.bestResponse = new Move(0, 0, Constants.X);
    }

            if ((this.givenBoard[0][1] != Constants.X || this.givenBoard[1][2] != Constants.X
                    || this.givenBoard[1][0] != Constants.O || this.givenBoard[2][1] != Constants.O)
                    || number_of_empty_cells != 5) {

                if ((this.givenBoard[0][2] == Constants.X && this.givenBoard[2][1] == Constants.X
                        && this.givenBoard[0][1] == Constants.O && this.givenBoard[1][0] == Constants.O)
                        && number_of_empty_cells == 5) {
                    return this.bestResponse = new Move(2, 2, Constants.X);
                }

                br = casesThatSuggestWinOrNoLose(number_of_empty_cells, 4);
                if (br != null) return br;
                if ((this.givenBoard[0][1] == Constants.X && this.givenBoard[1][0] == Constants.X
                        && this.givenBoard[1][2] == Constants.O && this.givenBoard[2][1] == Constants.O)
                        && number_of_empty_cells == 4) {
                    return this.bestResponse = new Move(2, 2, Constants.X);
                }

                if ((this.givenBoard[0][1] != Constants.X || this.givenBoard[1][2] != Constants.X
                        || this.givenBoard[1][0] != Constants.O || this.givenBoard[2][1] != Constants.O)
                        || number_of_empty_cells != 4) {

                    if ((this.givenBoard[0][2] == Constants.X && this.givenBoard[2][1] == Constants.X
                            && this.givenBoard[0][1] == Constants.O && this.givenBoard[1][0] == Constants.O)
                            && number_of_empty_cells == 4) {
                        return this.bestResponse = new Move(0, 0, Constants.X);
                    }

                    br = casesThatSuggestWinOrNoLose(number_of_empty_cells, 3);
                    if (br != null) return br;

                    br = casesThatSuggestWinOrNoLose(number_of_empty_cells, 2);
                    if (br != null) return br;

                    br = casesThatSuggestWinOrNoLose(number_of_empty_cells, 1);
                    if (br != null) return br;

                    List<List<Integer>> emptyCells = new ArrayList<>();
                    for (int row = 0; row < 3; row++) {
                        for (int col = 0; col < 3; col++) {
                            if (this.givenBoard[row][col] == Constants.EMPTY) {
                                List<Integer> emptyCell = new ArrayList<>();
                                emptyCell.add(row);
                                emptyCell.add(col);
                                emptyCells.add(emptyCell);
                            }
                        }
                    }

                    if (emptyCells.size() > 0) {
                        int random_number = r.nextInt(emptyCells.size()) + 1;
                        if (number_of_empty_cells % 2 == 1) {
                            return this.bestResponse = new Move(emptyCells.get(random_number - 1).get(0),
                                    emptyCells.get(random_number - 1).get(1), Constants.X);
                        } else if (number_of_empty_cells % 2 == 0) {
                            return this.bestResponse = new Move(emptyCells.get(random_number - 1).get(0),
                                    emptyCells.get(random_number - 1).get(1), Constants.O);
                        }
                    }
                    return null;
                } else {
            return this.bestResponse = new Move(2, 0, Constants.X);
        }

            } else {
                return this.bestResponse = new Move(0, 2, Constants.X);
            }

        }

    }


    private Move casesThatSuggestWinOrNoLose(int number_of_empty_cells, int given_number_of_empty_cells) {
        int currentPlayer, otherPlayer;
        if (number_of_empty_cells % 2 == 1) {
            currentPlayer = Constants.X;
            otherPlayer = Constants.O;
        } else {
            currentPlayer = Constants.O;
            otherPlayer = Constants.X;
        }

        if (((this.givenBoard[1][1] == currentPlayer && this.givenBoard[2][2] == currentPlayer)
                || (this.givenBoard[0][1] == currentPlayer && this.givenBoard[0][2] == currentPlayer)
                || (this.givenBoard[1][0] == currentPlayer && this.givenBoard[2][0] == currentPlayer))
                && this.givenBoard[0][0] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(0, 0, currentPlayer);
        }

        if (((this.givenBoard[0][0] == currentPlayer && this.givenBoard[0][2] == currentPlayer)
                || (this.givenBoard[1][1] == currentPlayer && this.givenBoard[2][1] == currentPlayer))
                && this.givenBoard[0][1] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(0, 1, currentPlayer);
        }

        if (((this.givenBoard[1][1] == currentPlayer && this.givenBoard[2][0] == currentPlayer)
                || (this.givenBoard[0][0] == currentPlayer && this.givenBoard[0][1] == currentPlayer)
                || (this.givenBoard[1][2] == currentPlayer && this.givenBoard[2][2] == currentPlayer))
                && this.givenBoard[0][2] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(0, 2, currentPlayer);
        }

        if (((this.givenBoard[0][0] == currentPlayer && this.givenBoard[2][0] == currentPlayer)
                || (this.givenBoard[1][1] == currentPlayer && this.givenBoard[1][2] == currentPlayer))
                && this.givenBoard[1][0] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(1, 0, currentPlayer);
        }

        if (((this.givenBoard[0][0] == currentPlayer && this.givenBoard[2][2] == currentPlayer)
                || (this.givenBoard[0][1] == currentPlayer && this.givenBoard[2][1] == currentPlayer)
                || (this.givenBoard[0][2] == currentPlayer && this.givenBoard[2][0] == currentPlayer)
                || (this.givenBoard[1][0] == currentPlayer && this.givenBoard[1][2] == currentPlayer))
                && this.givenBoard[1][1] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(1, 1, currentPlayer);
        }

        if (((this.givenBoard[0][2] == currentPlayer && this.givenBoard[2][2] == currentPlayer)
                || (this.givenBoard[1][0] == currentPlayer && this.givenBoard[1][1] == currentPlayer))
                && this.givenBoard[1][2] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(1, 2, currentPlayer);
        }

        if (((this.givenBoard[0][2] == currentPlayer && this.givenBoard[1][1] == currentPlayer)
                || (this.givenBoard[2][1] == currentPlayer && this.givenBoard[2][2] == currentPlayer)
                || (this.givenBoard[0][0] == currentPlayer && this.givenBoard[1][0] == currentPlayer))
                && this.givenBoard[2][0] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(2, 0, currentPlayer);
        }

        if (((this.givenBoard[2][0] == currentPlayer && this.givenBoard[2][2] == currentPlayer)
                || (this.givenBoard[0][1] == currentPlayer && this.givenBoard[1][1] == currentPlayer))
                && this.givenBoard[2][1] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(2, 1, currentPlayer);
        }

        if (((this.givenBoard[0][0] == currentPlayer && this.givenBoard[1][1] == currentPlayer)
                || (this.givenBoard[2][0] == currentPlayer && this.givenBoard[2][1] == currentPlayer)
                || (this.givenBoard[0][2] == currentPlayer && this.givenBoard[1][2] == currentPlayer))
                && this.givenBoard[2][2] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(2, 2, currentPlayer);
        }

        if (((this.givenBoard[1][1] == otherPlayer && this.givenBoard[2][2] == otherPlayer)
                || (this.givenBoard[0][1] == otherPlayer && this.givenBoard[0][2] == otherPlayer)
                || (this.givenBoard[1][0] == otherPlayer && this.givenBoard[2][0] == otherPlayer))
                && this.givenBoard[0][0] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(0, 0, currentPlayer);
        }

        if (((this.givenBoard[0][0] == otherPlayer && this.givenBoard[0][2] == otherPlayer)
                || (this.givenBoard[1][1] == otherPlayer && this.givenBoard[2][1] == otherPlayer))
                && this.givenBoard[0][1] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(0, 1, currentPlayer);
        }

        if (((this.givenBoard[1][1] == otherPlayer && this.givenBoard[2][0] == otherPlayer)
                || (this.givenBoard[0][0] == otherPlayer && this.givenBoard[0][1] == otherPlayer)
                || (this.givenBoard[1][2] == otherPlayer && this.givenBoard[2][2] == otherPlayer))
                && this.givenBoard[0][2] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(0, 2, currentPlayer);
        }

        if (((this.givenBoard[0][0] == otherPlayer && this.givenBoard[2][0] == otherPlayer)
                || (this.givenBoard[1][1] == otherPlayer && this.givenBoard[1][2] == otherPlayer))
                && this.givenBoard[1][0] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(1, 0, currentPlayer);
        }

        if (((this.givenBoard[0][0] == otherPlayer && this.givenBoard[2][2] == otherPlayer)
                || (this.givenBoard[0][1] == otherPlayer && this.givenBoard[2][1] == otherPlayer)
                || (this.givenBoard[0][2] == otherPlayer && this.givenBoard[2][0] == otherPlayer)
                || (this.givenBoard[1][0] == otherPlayer && this.givenBoard[1][2] == otherPlayer))
                && this.givenBoard[1][1] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(1, 1, currentPlayer);
        }

        if (((this.givenBoard[0][2] == otherPlayer && this.givenBoard[2][2] == otherPlayer)
                || (this.givenBoard[1][0] == otherPlayer && this.givenBoard[1][1] == otherPlayer))
                && this.givenBoard[1][2] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(1, 2, currentPlayer);
        }

        if (((this.givenBoard[0][2] == otherPlayer && this.givenBoard[1][1] == otherPlayer)
                || (this.givenBoard[2][1] == otherPlayer && this.givenBoard[2][2] == otherPlayer)
                || (this.givenBoard[0][0] == otherPlayer && this.givenBoard[1][0] == otherPlayer))
                && this.givenBoard[2][0] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(2, 0, currentPlayer);
        }

        if (((this.givenBoard[2][0] == otherPlayer && this.givenBoard[2][2] == otherPlayer)
                || (this.givenBoard[0][1] == otherPlayer && this.givenBoard[1][1] == otherPlayer))
                && this.givenBoard[2][1] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(2, 1, currentPlayer);
        }

        if (((this.givenBoard[0][0] == otherPlayer && this.givenBoard[1][1] == otherPlayer)
                || (this.givenBoard[2][0] == otherPlayer && this.givenBoard[2][1] == otherPlayer)
                || (this.givenBoard[0][2] == otherPlayer && this.givenBoard[1][2] == otherPlayer))
                && this.givenBoard[2][2] == Constants.EMPTY
                && number_of_empty_cells == given_number_of_empty_cells) {
            return this.bestResponse = new Move(2, 2, currentPlayer);
        }
        return null;
    }
}