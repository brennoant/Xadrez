package model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer {
    private long whiteTimeMillis;
    private long blackTimeMillis;
    private Timer timerWhite;
    private Timer timerBlack;
    private Runnable timeoutCallback;
    private boolean isWhiteTurn;

    public GameTimer(long initialTime, Runnable timeoutCallback) {
        this.whiteTimeMillis = initialTime;
        this.blackTimeMillis = initialTime;
        this.timeoutCallback = timeoutCallback;
        this.isWhiteTurn = true;

        timerWhite = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                whiteTimeMillis -= 1000;
                if (whiteTimeMillis <= 0) {
                    stop();
                    timeoutCallback.run();
                }
            }
        });

        timerBlack = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blackTimeMillis -= 1000;
                if (blackTimeMillis <= 0) {
                    stop();
                    timeoutCallback.run();
                }
            }
        });
    }

    public void startWhiteTimer() {
        timerWhite.start();
    }

    public void switchTurn() {
        if (isWhiteTurn) {
            timerWhite.stop();
            timerBlack.start();
        } else {
            timerBlack.stop();
            timerWhite.start();
        }
        isWhiteTurn = !isWhiteTurn;
    }

    public void stop() {
        timerWhite.stop();
        timerBlack.stop();
    }

    public long getWhiteTimeMillis() {
        return whiteTimeMillis;
    }

    public long getBlackTimeMillis() {
        return blackTimeMillis;
    }
}
