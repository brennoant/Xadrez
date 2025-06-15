package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChessView extends JFrame {
    private JButton[][] buttons = new JButton[8][8];
    private JLabel statusLabel;

    public ChessView(ActionListener listener) {
        setTitle("Jogo de Xadrez - MVC");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        Font pieceFont = new Font("Serif", Font.PLAIN, 36);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                button.setFont(pieceFont);
                button.setActionCommand(row + "," + col);
                button.addActionListener(listener);
                button.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        statusLabel = new JLabel("Bem-vindo ao Xadrez!", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void updateBoard(Board board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                buttons[row][col].setText(piece != null ? piece.getSymbol() : "");
            }
        }
    }

    public void highlightSquare(int row, int col) {
        resetHighlight();
        buttons[row][col].setBackground(Color.YELLOW);
    }

    public void resetHighlight() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                buttons[row][col].setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
            }
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void setStatusText(String text) {
        statusLabel.setText(text);
    }
}
