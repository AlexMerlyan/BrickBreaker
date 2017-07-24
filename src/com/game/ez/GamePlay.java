package com.game.ez;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Arizel on 24.07.2017.
 */
public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean isPlay;
    private int score;
    private int totalBricks;
    private int delay;

    private int playerX;

    private int ballSpeed;
    private int ballPosX;
    private int ballPosY;
    private int ballDirX;
    private int ballDirY;

    private Timer timer;

    public GamePlay() {
        this.totalBricks = 21;
        this.delay = 8;
        this.playerX = 395;
        this.ballDirX = -1;
        this.ballDirY = -2;
        this.ballPosY = 350;
        this.ballPosX = 120;
        this.ballSpeed = 10;
        this.timer = new Timer(this.delay, this);
        this.timer.start();

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.GRAY);
        g.fillRect(1 , 1, 792, 592);

        //borders
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 792, 3);
        g.fillRect(792, 0, 3, 592);
        g.fillRect(0, 570, 792, 3);

        //paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballPosX, ballPosY, 20, 20);

       // g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (isPlay) {
            ballPosX = ballPosX + ballDirX;
            ballPosY = ballPosY + ballDirY;

            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballDirY = -ballDirY;
                ballDirY = ballDirY - 2;
                ballDirX = ballDirX - 1;
            }

            if (ballPosX >= 800) {
                ballDirX = -ballDirX;
            }
            if (ballPosX <= 0) {
                ballDirX = -ballDirX;
            }
            if (ballPosY <= 0) {
                ballDirY = -ballDirY;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 690) {
                playerX = 690;
            } else {
                moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 5) {
                playerX = 5;
            } else {
                moveLeft();
            }
        }
    }

    private void moveLeft() {
        isPlay = true;
        playerX -= 10;
    }

    private void moveRight() {
        isPlay = true;
        playerX += 10;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
