package model;

public class GameRules {

    public static boolean isKingInCheck(Board board, boolean whiteKing) {
        int kingRow = -1, kingCol = -1;

        // Localiza o rei do jogador
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

        // Verifica se alguma peça inimiga pode capturar o rei
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
        if (!isKingInCheck(board, whiteKing)) {
            return false;  // Não é xeque-mate se o rei não estiver em xeque
        }

        // Testa todas as jogadas possíveis de todas as peças do jogador
        for (int fromRow = 0; fromRow < 8; fromRow++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                Piece piece = board.getPiece(fromRow, fromCol);
                if (piece != null && piece.isWhite() == whiteKing) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            if (piece.isValidMove(toRow, toCol, board)) {
                                Board clone = cloneBoard(board);
                                clone.movePiece(fromRow, fromCol, toRow, toCol);

                                // Após a simulação do movimento, verifica se o rei ainda estaria em xeque
                                if (!isKingInCheck(clone, whiteKing)) {
                                    return false;  // Existe pelo menos um movimento que salva o rei
                                }
                            }
                        }
                    }
                }
            }
        }

        // Se nenhum movimento possível salva o rei → xeque-mate confirmado
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
