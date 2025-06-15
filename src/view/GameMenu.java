package view;

import javax.swing.*;

public class GameMenu {
    private String selectedMode;
    private String selectedTime;

    public GameMenu() {
        selectMode();
        selectTime();
    }

    private void selectMode() {
        String[] options = {"Jogador vs Jogador", "Jogador vs BOT"};
        selectedMode = (String) JOptionPane.showInputDialog(null, "Selecione o modo de jogo:", "Modo de Jogo",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    private void selectTime() {
        String[] options = {"Bullet (1 min)", "Blitz (5 min)", "Rápida (15 min)", "Clássica (60 min)"};
        selectedTime = (String) JOptionPane.showInputDialog(null, "Selecione o tempo de jogo:", "Tempo",
                JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public String getSelectedTime() {
        return selectedTime;
    }
}
