package model;

import java.io.Serializable;

/**
 * Classe abstrata base para todas as peças de xadrez.
 * Agora implementa a interface Movable.
 *
 * Autor: Brenno Soares
 */
public abstract class Piece implements Movable, Serializable {
    protected int row;
    protected int col;
    protected boolean isWhite;

    public Piece(int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Método abstrato que define o símbolo da peça (ex: ♔, ♕, ♖, etc).
     *
     * @return Símbolo da peça
     */
    public abstract String getSymbol();
}
