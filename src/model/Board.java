package model;

import java.io.Serializable;

/**
 * Classe que representa o tabuleiro do jogo de xadrez.
 * Responsável por armazenar as peças, realizar movimentações
 * e manter o último movimento (usado para En Passant).
 *
 * Autor: Brenno Soares
 */
public class Board implements Serializable {
    private Piece[][] board;
    private Move lastMove;

    public Board() {
        board = new Piece[8][8];
        setupInitialBoard();
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
        if (piece != null) {
            piece.setPosition(row, col);
        }
    }

    /**
     * Executa um movimento de peça no tabuleiro.
     * Inclui tratamento especial para En Passant e Roque.
     *
     * @param fromRow Linha de origem
     * @param fromCol Coluna de origem
     * @param toRow Linha de destino
     * @param toCol Coluna de destino
     */
    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = getPiece(fromRow, fromCol);

        if (piece == null) return;

        Piece target = getPiece(toRow, toCol);

        // Tratamento de En Passant
        if (piece instanceof Pawn) {
            if (fromCol != toCol && target == null) {
                int capturedRow = piece.isWhite() ? toRow + 1 : toRow - 1;
                setPiece(capturedRow, toCol, null);
            }
        }

        // Move a peça para a nova posição
        setPiece(toRow, toCol, piece);
        setPiece(fromRow, fromCol, null);

        // CORREÇÃO DO ROQUE: Move automaticamente a Torre
        if (piece instanceof King && Math.abs(toCol - fromCol) == 2) {
            if (toCol == 6) { // Roque pequeno
                Piece rook = getPiece(fromRow, 7);
                setPiece(fromRow, 5, rook);
                setPiece(fromRow, 7, null);
            } else if (toCol == 2) { // Roque grande
                Piece rook = getPiece(fromRow, 0);
                setPiece(fromRow, 3, rook);
                setPiece(fromRow, 0, null);
            }
        }

        // Atualiza o último movimento
        lastMove = new Move(fromRow, fromCol, toRow, toCol);
    }

    /**
     * Tenta realizar uma jogada, validando com o método isValidMove da peça.
     *
     * @return true se a jogada for válida
     */
    public boolean tryMove(int fromRow, int fromCol, int toRow, int toCol, boolean whiteTurn) {
        Piece piece = getPiece(fromRow, fromCol);
        if (piece != null && piece.isWhite() == whiteTurn && piece.isValidMove(toRow, toCol, this)) {
            movePiece(fromRow, fromCol, toRow, toCol);
            return true;
        }
        return false;
    }

    public Move getLastMove() {
        return lastMove;
    }

    /**
     * Configuração inicial das peças no tabuleiro.
     */
    private void setupInitialBoard() {
        for (int col = 0; col < 8; col++) {
            setPiece(1, col, new Pawn(1, col, false)); // Peões pretos
            setPiece(6, col, new Pawn(6, col, true));  // Peões brancos
        }

        setPiece(0, 0, new Rook(0, 0, false));
        setPiece(0, 7, new Rook(0, 7, false));
        setPiece(7, 0, new Rook(7, 0, true));
        setPiece(7, 7, new Rook(7, 7, true));

        setPiece(0, 1, new Knight(0, 1, false));
        setPiece(0, 6, new Knight(0, 6, false));
        setPiece(7, 1, new Knight(7, 1, true));
        setPiece(7, 6, new Knight(7, 6, true));

        setPiece(0, 2, new Bishop(0, 2, false));
        setPiece(0, 5, new Bishop(0, 5, false));
        setPiece(7, 2, new Bishop(7, 2, true));
        setPiece(7, 5, new Bishop(7, 5, true));

        setPiece(0, 3, new Queen(0, 3, false));
        setPiece(7, 3, new Queen(7, 3, true));

        setPiece(0, 4, new King(0, 4, false));
        setPiece(7, 4, new King(7, 4, true));
    }

    /**
     * Cria uma cópia profunda do tabuleiro atual (usado para simulações).
     */
    public Board cloneBoard() {
        Board clone = new Board();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                clone.board[row][col] = this.board[row][col];
            }
        }
        clone.lastMove = this.lastMove;
        return clone;
    }
}
