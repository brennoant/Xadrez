package model;

import java.io.Serializable;

public abstract class Piece implements Serializable {
    protected int row;
    protected int col;
    protected boolean isWhite;

    public Piece(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    public abstract boolean isValidMove(int targetRow, int targetCol, Board board);

    public int getRow() { return row; }
    public int getCol() { return col; }
    public boolean isWhite() { return isWhite; }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public abstract String getSymbol();
}
