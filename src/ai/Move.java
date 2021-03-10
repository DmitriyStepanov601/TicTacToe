package ai;

import java.io.Serializable;

/**
 * A class that describes movement in the game
 *
 * @author Dmitriy Stepanov
 */
public class Move implements Serializable {
    private int row;
    private int column;
    private int value;

    /**
     * Constructor - create a new move on the playing field
     *
     * @see Move#Move()
     */
    public Move() {
        this.row = 0;
        this.column = 0;
    }

    /**
     * Constructor - create a new move on the playing field
     *
     * @param row - row of the playing field
     * @param col - column of the playing field
     * @see Move#Move(int, int)
     */
    public Move(int row, int col) {
        this.row = row;
        this.column = col;
        this.value = 0;
    }

    /**
     * Constructor - create a new move on the playing field
     *
     * @param value - value
     * @see Move#Move(int)
     */
    public Move(int value) {
        this.row = 0;
        this.column = 0;
        this.value = value;
    }

    /**
     * Constructor - create a new move on the playing field
     *
     * @param row   - row of the playing field
     * @param col   - column of the playing field
     * @param value - value
     * @see Move#Move(int, int, int)
     */
    public Move(int row, int col, int value) {
        this.row = row;
        this.column = col;
        this.value = value;
    }

    /**
     * Constructor - create a new move on the playing field
     *
     * @param otherMove - other move
     * @see Move#Move(Move)
     */
    public Move(Move otherMove) {
        this.row = otherMove.getRow();
        this.column = otherMove.getColumn();
        this.value = otherMove.getValue();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getValue() {
        return value;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "move [" + row + "][" + column + "]: " + value;
    }
}