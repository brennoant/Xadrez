package model;

/**
 * Classe que representa o Peão no jogo de xadrez.
 * Inclui movimentação básica, captura, avanço duplo inicial e En Passant.
 *
 * Autor: Brenno Soares
 */
public class Pawn extends Piece {
    private boolean hasMoved;

    public Pawn(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
        hasMoved = false;
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, Board board) {
        int direction = isWhite ? -1 : 1;
        int startRow = isWhite ? 6 : 1;
        int rowDiff = targetRow - row;
        int colDiff = Math.abs(targetCol - col);
        Piece targetPiece = board.getPiece(targetRow, targetCol);

        // Avanço simples
        if (colDiff == 0) {
            if (rowDiff == direction && targetPiece == null) {
                return true;
            }
            // Avanço duplo na primeira jogada
            if (row == startRow && rowDiff == 2 * direction && targetPiece == null) {
                int betweenRow = row + direction;
                if (board.getPiece(betweenRow, col) == null) {
                    return true;
                }
            }
        }

        // Captura normal na diagonal
        if (colDiff == 1 && rowDiff == direction) {
            if (targetPiece != null && targetPiece.isWhite() != isWhite) {
                return true;
            }

            // Captura En Passant
            Move lastMove = board.getLastMove();
            if (lastMove != null) {
                Piece lastMovedPiece = board.getPiece(lastMove.getToRow(), lastMove.getToCol());
                if (lastMovedPiece instanceof Pawn &&
                    Math.abs(lastMove.getFromRow() - lastMove.getToRow()) == 2 &&
                    lastMove.getToRow() == row &&
                    lastMove.getToCol() == targetCol) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "♙" : "♟";
    }

    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        hasMoved = true;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
}
