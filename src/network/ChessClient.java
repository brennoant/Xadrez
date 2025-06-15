package network;

import model.GameState;

import java.io.*;
import java.net.Socket;

public class ChessClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private GameState gameState;

    public ChessClient(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            startGameLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGameLoop() {
        try {
            while (true) {
                // Recebe o estado atual do servidor
                gameState = (GameState) in.readObject();
                System.out.println("Estado do jogo recebido do servidor.");

                // Aqui você pode implementar uma interface de cliente
                // Para este exemplo: apenas salva o estado local e devolve após uma jogada fictícia
                // (Depois você pode integrar com a ChessView e o Controller local)

                // Exemplo simples: só envia o estado de volta (como se tivesse feito uma jogada)
                out.writeObject(gameState);
                out.flush();
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
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChessClient("localhost", 12345);
    }
}
