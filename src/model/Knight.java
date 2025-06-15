package model;

public class Knight extends Piece {
    public Knight(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, Board board) {
        int dRow = Math.abs(targetRow - row);
        int dCol = Math.abs(targetCol - col);

        if ((dRow == 2 && dCol == 1) || (dRow == 1 && dCol == 2)) {
            Piece targetPiece = board.getPiece(targetRow, targetCol);
            return targetPiece == null || targetPiece.isWhite() != isWhite;
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "♘" : "♞";
    }
}
