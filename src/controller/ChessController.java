package controller;

import bot.ChessBot;
import model.*;
import view.ChessView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ChessController implements ActionListener {
    private GameState gameState;
    private ChessView view;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private GameTimer gameTimer;
    private ChessBot bot = new ChessBot();
    private Ranking ranking = new Ranking();
    private boolean versusBot;
    private boolean gameEnded = false;
    private String playerWhiteName;
    private String playerBlackName;

    public ChessController(String mode, String time, String playerWhiteName, String playerBlackName) {
        this.playerWhiteName = playerWhiteName;
        this.playerBlackName = playerBlackName;
        versusBot = mode.equals("Jogador vs BOT");

        gameState = new GameState();
        view = new ChessView(this);

        long initialTime = getTimeFromSelection(time);
        gameTimer = new GameTimer(initialTime, this::onTimeout);

        view.updateBoard(gameState.getBoard());
        updateStatus();

        if (versusBot && !gameState.isWhiteTurn()) {
            botMove();
        }
    }

    private long getTimeFromSelection(String selection) {
        switch (selection) {
            case "Bullet (1 min)": return 60_000;
            case "Blitz (5 min)": return 300_000;
            case "Rápida (15 min)": return 900_000;
            case "Clássica (60 min)": return 3600_000;
            default: return 300_000;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameEnded) return;

        String command = e.getActionCommand();
        String[] parts = command.split(",");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);

        if (selectedRow == -1 && selectedCol == -1) {
            Piece piece = gameState.getBoard().getPiece(row, col);
            if (piece != null && piece.isWhite() == gameState.isWhiteTurn()) {
                selectedRow = row;
                selectedCol = col;
                view.highlightSquare(row, col);
            }
        } else {
            if (gameState.getBoard().tryMove(selectedRow, selectedCol, row, col, gameState.isWhiteTurn())) {
                posJogada(row, col);
                if (versusBot && !gameEnded && !gameState.isWhiteTurn()) {
                    botMove();
                }
            } else {
                view.showMessage("Jogada inválida!");
            }
            selectedRow = -1;
            selectedCol = -1;
            view.resetHighlight();
        }
    }

    private void posJogada(int toRow, int toCol) {
        Board board = gameState.getBoard();

        Piece movedPiece = board.getPiece(toRow, toCol);
        if (movedPiece instanceof Pawn) {
            if ((movedPiece.isWhite() && toRow == 0) || (!movedPiece.isWhite() && toRow == 7)) {
                board.setPiece(toRow, toCol, new Queen(toRow, toCol, movedPiece.isWhite()));
                view.showMessage("Peão promovido a Rainha!");
            }
        }

        gameState.switchTurn();
        gameTimer.switchTurn();
        view.updateBoard(board);
        gameState.saveGame("src/resources/saved_game.dat");
        updateStatus();
        verificarEstadoFinal();
    }

    private void botMove() {
        Move botMove = bot.getBestMove(gameState.getBoard(), false);
        if (botMove != null) {
            if (gameState.getBoard().tryMove(botMove.getFromRow(), botMove.getFromCol(), botMove.getToRow(), botMove.getToCol(), false)) {
                posJogada(botMove.getToRow(), botMove.getToCol());
            }
        }
    }

    private void verificarEstadoFinal() {
        boolean nextPlayerWhite = gameState.isWhiteTurn();
        Board board = gameState.getBoard();

        if (GameRules.isKingInCheck(board, nextPlayerWhite)) {
            view.showMessage("Xeque!");

            if (GameRules.isCheckmate(board, nextPlayerWhite)) {
                String vencedor = !nextPlayerWhite ? playerWhiteName : playerBlackName;
                view.showMessage("Xeque-mate! Vitória de " + vencedor);
                ranking.addWin(vencedor);
                mostrarRanking();
                encerrarJogo();
            }
        } else if (GameRules.isCheckmate(board, nextPlayerWhite)) {
            String vencedor = !nextPlayerWhite ? playerWhiteName : playerBlackName;
            view.showMessage("Xeque-mate! Vitória de " + vencedor);
            ranking.addWin(vencedor);
            mostrarRanking();
            encerrarJogo();
        }
    }

    private void onTimeout() {
        String vencedor = !gameState.isWhiteTurn() ? playerWhiteName : playerBlackName;
        view.showMessage("Tempo esgotado! Vitória de " + vencedor);
        ranking.addWin(vencedor);
        mostrarRanking();
        encerrarJogo();
    }

    private void mostrarRanking() {
        StringBuilder sb = new StringBuilder("Ranking Atual:\n");
        for (String entry : ranking.getRanking()) {
            sb.append(entry).append("\n");
        }
        view.showMessage(sb.toString());
    }

    private void encerrarJogo() {
        gameEnded = true;
        gameTimer.stop();
        System.exit(0);
    }

    private void updateStatus() {
        String turno = gameState.isWhiteTurn() ? playerWhiteName + " (Brancas)" : playerBlackName + " (Pretas)";
        String timeWhite = formatTime(gameTimer.getWhiteTimeMillis());
        String timeBlack = formatTime(gameTimer.getBlackTimeMillis());
        view.setStatusText("Turno: " + turno + " | Tempo Brancas: " + timeWhite + " | Tempo Pretas: " + timeBlack);
    }

    private String formatTime(long millis) {
        long seconds = millis / 1000;
        long min = seconds / 60;
        long sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
}
