import controller.ChessController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String[] options = {"Jogador vs Jogador", "Jogador vs BOT", "Continuar Jogo Salvo", "Sair"};
        int modeChoice = JOptionPane.showOptionDialog(
                null,
                "Selecione o modo de jogo:",
                "Modo de Jogo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (modeChoice == 3 || modeChoice == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        if (modeChoice == 2) {
            ChessController controller = new ChessController("Jogador vs Jogador", "Blitz (5 min)", "Jogador1", "Jogador2");
            controller.loadSavedGame("src/resources/saved_game.dat");
            return;
        }

        String[] tempos = {"Bullet (1 min)", "Blitz (5 min)", "Rápida (15 min)", "Clássica (60 min)"};
        int timeChoice = JOptionPane.showOptionDialog(
                null,
                "Selecione o tempo de jogo:",
                "Tempo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                tempos,
                tempos[0]
        );

        if (timeChoice == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        String timeSelection = tempos[timeChoice];
        String player1 = JOptionPane.showInputDialog("Nome do jogador das Brancas:");
        if (player1 == null) System.exit(0);

        String player2;
        if (modeChoice == 0) {
            player2 = JOptionPane.showInputDialog("Nome do jogador das Pretas:");
            if (player2 == null) System.exit(0);
        } else {
            player2 = "BOT";
        }

        String mode = (modeChoice == 0) ? "Jogador vs Jogador" : "Jogador vs BOT";
        new ChessController(mode, timeSelection, player1, player2);
    }
}
