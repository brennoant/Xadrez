package model;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private long whiteTimeMillis;
    private long blackTimeMillis;
    private long interval = 1000; // Atualização a cada segundo
    private boolean whiteTurn;
    private Timer timer;
    private Runnable onTimeout;

    public GameTimer(long initialMillis, Runnable onTimeout) {
        this.whiteTimeMillis = initialMillis;
        this.blackTimeMillis = initialMillis;
        this.onTimeout = onTimeout;
        startTimer();
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (whiteTurn) whiteTimeMillis -= interval;
                else blackTimeMillis -= interval;

                if (whiteTimeMillis <= 0 || blackTimeMillis <= 0) {
                    timer.cancel();
                    SwingUtilities.invokeLater(onTimeout);
                }
            }
        }, 0, interval);
    }

    public void switchTurn() {
        whiteTurn = !whiteTurn;
    }

    public long getWhiteTimeMillis() {
        return whiteTimeMillis;
    }

    public long getBlackTimeMillis() {
        return blackTimeMillis;
    }

    public void stop() {
        if (timer != null) timer.cancel();
    }
}
