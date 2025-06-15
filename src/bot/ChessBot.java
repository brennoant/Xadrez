package bot;

import model.*;

public class ChessBot {

    private static final int MAX_DEPTH = 2;

    public Move getBestMove(Board board, boolean isWhite) {
        double bestScore = Double.NEGATIVE_INFINITY;
        Move bestMove = null;

        for (int fromRow = 0; fromRow < 8; fromRow++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                Piece piece = board.getPiece(fromRow, fromCol);
                if (piece != null && piece.isWhite() == isWhite) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            if (piece.isValidMove(toRow, toCol, board)) {
                                Board clone = cloneBoard(board);
                                if (clone.tryMove(fromRow, fromCol, toRow, toCol, isWhite)) {
                                    // Não deixa o próprio rei em xeque
                                    if (!GameRules.isKingInCheck(clone, isWhite)) {
                                        double score = minimax(clone, MAX_DEPTH - 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, !isWhite);
                                        if (score > bestScore) {
                                            bestScore = score;
                                            bestMove = new Move(fromRow, fromCol, toRow, toCol);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bestMove;
    }

    private double minimax(Board board, int depth, double alpha, double beta, boolean maximizingPlayer) {
        if (depth == 0) {
            return evaluateBoard(board, maximizingPlayer);
        }

        double bestEval = maximizingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

        for (int fromRow = 0; fromRow < 8; fromRow++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                Piece piece = board.getPiece(fromRow, fromCol);
                if (piece != null && piece.isWhite() == maximizingPlayer) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            if (piece.isValidMove(toRow, toCol, board)) {
                                Board clone = cloneBoard(board);
                                if (clone.tryMove(fromRow, fromCol, toRow, toCol, maximizingPlayer)) {
                                    if (!GameRules.isKingInCheck(clone, maximizingPlayer)) {
                                        double eval = minimax(clone, depth - 1, alpha, beta, !maximizingPlayer);
                                        if (maximizingPlayer) {
                                            bestEval = Math.max(bestEval, eval);
                                            alpha = Math.max(alpha, eval);
                                        } else {
                                            bestEval = Math.min(bestEval, eval);
                                            beta = Math.min(beta, eval);
                                        }
                                        if (beta <= alpha) break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bestEval;
    }

    private double evaluateBoard(Board board, boolean maximizingPlayer) {
        double score = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    score += getPieceValue(piece);
                }
            }
        }

        // Penaliza se o próprio rei estiver em xeque
        if (GameRules.isKingInCheck(board, maximizingPlayer)) {
            score -= 50;
        }

        // Bonifica se o rei inimigo estiver em xeque
        if (GameRules.isKingInCheck(board, !maximizingPlayer)) {
            score += 50;
        }

        return score;
    }

    private double getPieceValue(Piece piece) {
        if (piece instanceof Pawn) return piece.isWhite() ? 1 : -1;
        if (piece instanceof Knight || piece instanceof Bishop) return piece.isWhite() ? 3 : -3;
        if (piece instanceof Rook) return piece.isWhite() ? 5 : -5;
        if (piece instanceof Queen) return piece.isWhite() ? 9 : -9;
        if (piece instanceof King) return piece.isWhite() ? 100 : -100;
        return 0;
    }

    private Board cloneBoard(Board original) {
        Board clone = new Board();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                clone.setPiece(row, col, original.getPiece(row, col));
            }
        }
        return clone;
    }
}
