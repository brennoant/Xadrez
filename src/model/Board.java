package model;

import java.io.Serializable;

public class Board implements Serializable {
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        setupInitialBoard();
    }

    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
        if (piece != null) piece.setPosition(row, col);
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = getPiece(fromRow, fromCol);
        setPiece(toRow, toCol, piece);
        setPiece(fromRow, fromCol, null);
    }

    public boolean tryMove(int fromRow, int fromCol, int toRow, int toCol, boolean whiteTurn) {
        Piece piece = getPiece(fromRow, fromCol);
        if (piece != null && piece.isWhite() == whiteTurn && piece.isValidMove(toRow, toCol, this)) {
            movePiece(fromRow, fromCol, toRow, toCol);
            return true;
        }
        return false;
    }

    public Piece[][] getBoard() {
        return board;
    }

    private void setupInitialBoard() {
        // Peões
        for (int col = 0; col < 8; col++) {
            setPiece(1, col, new Pawn(1, col, false));
            setPiece(6, col, new Pawn(6, col, true));
        }

        // Torres
        setPiece(0, 0, new Rook(0, 0, false));
        setPiece(0, 7, new Rook(0, 7, false));
        setPiece(7, 0, new Rook(7, 0, true));
        setPiece(7, 7, new Rook(7, 7, true));

        // Cavalos
        setPiece(0, 1, new Knight(0, 1, false));
        setPiece(0, 6, new Knight(0, 6, false));
        setPiece(7, 1, new Knight(7, 1, true));
        setPiece(7, 6, new Knight(7, 6, true));

        // Bispos
        setPiece(0, 2, new Bishop(0, 2, false));
        setPiece(0, 5, new Bishop(0, 5, false));
        setPiece(7, 2, new Bishop(7, 2, true));
        setPiece(7, 5, new Bishop(7, 5, true));

        // Damas
        setPiece(0, 3, new Queen(0, 3, false));
        setPiece(7, 3, new Queen(7, 3, true));

        // Reis
        setPiece(0, 4, new King(0, 4, false));
        setPiece(7, 4, new King(7, 4, true));
    }
}
