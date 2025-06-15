package model;

public class Queen extends Piece {
    public Queen(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, Board board) {
        int dRow = targetRow - row;
        int dCol = targetCol - col;

        if (row == targetRow || col == targetCol) {
            int rowStep = Integer.compare(targetRow, row);
            int colStep = Integer.compare(targetCol, col);

            int currRow = row + rowStep;
            int currCol = col + colStep;

            while (currRow != targetRow || currCol != targetCol) {
                if (board.getPiece(currRow, currCol) != null) return false;
                currRow += rowStep;
                currCol += colStep;
            }

            Piece targetPiece = board.getPiece(targetRow, targetCol);
            return targetPiece == null || targetPiece.isWhite() != isWhite;
        }

        if (Math.abs(dRow) == Math.abs(dCol)) {
            int rowStep = Integer.compare(targetRow, row);
            int colStep = Integer.compare(targetCol, col);

            int currRow = row + rowStep;
            int currCol = col + colStep;

            while (currRow != targetRow && currCol != targetCol) {
                if (board.getPiece(currRow, currCol) != null) return false;
                currRow += rowStep;
                currCol += colStep;
            }

            Piece targetPiece = board.getPiece(targetRow, targetCol);
            return targetPiece == null || targetPiece.isWhite() != isWhite;
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "♕" : "♛";
    }
}
