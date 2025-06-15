import controller.ChessController;
import view.GameMenu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GameMenu menu = new GameMenu();

        if (menu.getSelectedMode() == null || menu.getSelectedTime() == null) {
            JOptionPane.showMessageDialog(null, "Jogo cancelado.");
            System.exit(0);
        }

        String playerWhite = JOptionPane.showInputDialog("Nome do Jogador das Brancas:");
        if (playerWhite == null || playerWhite.trim().isEmpty()) playerWhite = "Jogador Branco";

        String playerBlack = menu.getSelectedMode().equals("Jogador vs BOT") ? "BOT" :
                JOptionPane.showInputDialog("Nome do Jogador das Pretas:");
        if (playerBlack == null || playerBlack.trim().isEmpty()) playerBlack = "Jogador Preto";

        new ChessController(menu.getSelectedMode(), menu.getSelectedTime(), playerWhite, playerBlack);
    }
}
