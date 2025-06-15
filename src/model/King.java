package model;

public class King extends Piece {
    public King(int row, int col, boolean isWhite) {
        super(row, col, isWhite);
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, Board board) {
        int dRow = Math.abs(targetRow - row);
        int dCol = Math.abs(targetCol - col);

        // Movimento normal: 1 casa em qualquer direção
        if (dRow <= 1 && dCol <= 1) {
            Piece targetPiece = board.getPiece(targetRow, targetCol);
            return targetPiece == null || targetPiece.isWhite() != isWhite;
        }

        // Roque simples (sem controle de histórico de movimento)
        if (row == targetRow && Math.abs(targetCol - col) == 2) {
            int rookCol = targetCol > col ? 7 : 0;
            Piece rook = board.getPiece(row, rookCol);

            if (rook instanceof Rook && rook.isWhite() == isWhite) {
                int step = (targetCol - col) / 2;
                for (int c = col + step; c != rookCol; c += step) {
                    if (board.getPiece(row, c) != null) {
                        return false;
                    }
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public String getSymbol() {
        return isWhite ? "♔" : "♚";
    }
}
