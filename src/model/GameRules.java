package model;

public class GameRules {

    public static boolean isKingInCheck(Board board, boolean whiteKing) {
        int kingRow = -1, kingCol = -1;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece instanceof King && piece.isWhite() == whiteKing) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        if (kingRow == -1) return true;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null && piece.isWhite() != whiteKing) {
                    if (piece.isValidMove(kingRow, kingCol, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isCheckmate(Board board, boolean whiteKing) {
        if (!isKingInCheck(board, whiteKing)) return false;

        for (int fromRow = 0; fromRow < 8; fromRow++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                Piece piece = board.getPiece(fromRow, fromCol);
                if (piece != null && piece.isWhite() == whiteKing) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            if (piece.isValidMove(toRow, toCol, board)) {
                                Board clone = cloneBoard(board);
                                if (clone.tryMove(fromRow, fromCol, toRow, toCol, whiteKing)) {
                                    if (!isKingInCheck(clone, whiteKing)) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private static Board cloneBoard(Board original) {
        Board clone = new Board();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                clone.setPiece(row, col, original.getPiece(row, col));
            }
        }
        return clone;
    }
}
