package model;

public class Rook extends Piece {
    public Rook(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, Board board) {
        if (row != targetRow && col != targetCol) return false; // Só linha reta

        int rowStep = Integer.compare(targetRow, row);
        int colStep = Integer.compare(targetCol, col);

        int currRow = row + rowStep;
        int currCol = col + colStep;

        while (currRow != targetRow || currCol != targetCol) {
            if (board.getPiece(currRow, currCol) != null) {
                return false; // Bloqueio no caminho
            }
            currRow += rowStep;
            currCol += colStep;
        }

        Piece targetPiece = board.getPiece(targetRow, targetCol);
        return targetPiece == null || targetPiece.isWhite() != isWhite;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "♖" : "♜";
    }
}
