package model;

public class Pawn extends Piece {
    public Pawn(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, Board board) {
        int direction = isWhite ? -1 : 1;
        int startRow = isWhite ? 6 : 1;

        // Movimento simples para frente
        if (col == targetCol) {
            if (row + direction == targetRow && board.getPiece(targetRow, targetCol) == null) {
                return true;
            }

            // Movimento duplo no primeiro lance
            if (row == startRow && row + 2 * direction == targetRow &&
                    board.getPiece(row + direction, targetCol) == null &&
                    board.getPiece(targetRow, targetCol) == null) {
                return true;
            }
        }

        // Captura diagonal
        if (Math.abs(col - targetCol) == 1 && row + direction == targetRow) {
            Piece targetPiece = board.getPiece(targetRow, targetCol);
            if (targetPiece != null && targetPiece.isWhite() != isWhite) {
                return true;
            }

            // En Passant (não implementado nesta versão, mas pode adicionar depois)
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "♙" : "♟";
    }
}
