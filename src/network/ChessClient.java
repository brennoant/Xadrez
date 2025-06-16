package network;

import model.GameState;

import java.io.*;
import java.net.Socket;

/**
 * Cliente para o modo de jogo remoto via sockets.
 * Envia o estado atual do jogo para o servidor e aguarda uma resposta de confirmação.
 *
 * Autor: Brenno Soares
 */
public class ChessClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Conectado ao servidor!");

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Exemplo: Carregando um estado de jogo (poderia ser o estado atual durante o jogo)
            GameState currentState = GameState.loadGame("src/resources/saved_game.dat");

            if (currentState != null) {
                out.writeObject(currentState);
                System.out.println("Estado do jogo enviado ao servidor.");

                // Aguarda confirmação do servidor
                Object response = in.readObject();
                System.out.println("Resposta do servidor: " + response);
            } else {
                System.out.println("Falha ao carregar o estado do jogo para enviar.");
            }

            out.close();
            in.close();
            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
