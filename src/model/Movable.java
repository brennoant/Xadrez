package model;

public interface Movable {
    boolean isValidMove(int targetRow, int targetCol, Board board);
}
