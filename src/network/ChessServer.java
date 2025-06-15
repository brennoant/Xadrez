package network;

import model.GameState;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChessServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private GameState gameState;

    public ChessServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor aguardando conexão...");
            clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado!");

            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            gameState = new GameState();
            startGameLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGameLoop() {
        try {
            while (true) {
                // Envia estado atual para o cliente
                out.writeObject(gameState);
                out.flush();

                // Aguarda jogada do cliente
                GameState newState = (GameState) in.readObject();
                if (newState != null) {
                    gameState = newState;
                    System.out.println("Estado do jogo atualizado pelo cliente.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    private void closeConnections() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChessServer(12345);
    }
}
