package network;

import model.GameState;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor para o modo de jogo remoto via sockets.
 * Agora evita o loop infinito ao receber o estado do jogo.
 *
 * Autor: Brenno Soares
 */
public class ChessServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor iniciado. Aguardando conexão...");

            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado!");

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            try {
                // Recebe o estado do jogo apenas uma vez
                Object receivedObject = in.readObject();

                if (receivedObject instanceof GameState) {
                    GameState receivedState = (GameState) receivedObject;
                    System.out.println("Estado do jogo recebido no servidor.");

                    // Aqui você pode salvar o estado, atualizar o jogo ou apenas responder
                    out.writeObject("Servidor: Estado recebido com sucesso!");
                } else {
                    System.out.println("Objeto recebido não é um GameState.");
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // Fecha os streams e o socket após o processamento
            in.close();
            out.close();
            socket.close();
            System.out.println("Conexão encerrada.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
