package model;

import java.io.*;

public class GameState implements Serializable {
    private Board board;
    private boolean whiteTurn;

    public GameState() {
        board = new Board();
        whiteTurn = true;
    }

    public Board getBoard() { return board; }
    public boolean isWhiteTurn() { return whiteTurn; }
    public void switchTurn() { whiteTurn = !whiteTurn; }

    public void saveGame(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameState loadGame(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameState) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
