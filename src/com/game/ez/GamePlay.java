package com.game.ez;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

/**
 * Created by Arizel on 24.07.2017.
 */
class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean isPlay;
    private boolean isTest;
    private int score;
    private int totalBricks;
    private int delay;
    private int row;
    private int col;

    private int playerX;

    private int ballPosX;
    private int ballPosY;
    private int ballDirX;
    private int ballDirY;

    private Date timeOfStartGame;
    private Timer timer;
    private MapGenerator mapGenerator;

    public GamePlay(int row, int col) {
        this.row = row;
        this.col = col;
        this.totalBricks = row * col;
        System.out.println(totalBricks);
        this.delay = 8;
        this.playerX = 395;
        this.ballDirX = -1;
        this.ballDirY = -2;
        this.ballPosY = 350;
        this.ballPosX = 120;
        this.timeOfStartGame = new Date();
        this.mapGenerator = new MapGenerator(row, col);
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

        //map
        mapGenerator.drawMap((Graphics2D) g);

        //score
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(String.valueOf(score), 590, 30);

        //paddle
        g.setColor(Color.GREEN);
        if (isTest) {
            playerX = ballPosX - 50;
            g.fillRect(playerX, 550, 100, 8);
        } else {
            g.fillRect(playerX, 550, 100, 8);
        }

        //ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        if (totalBricks <= 1) {
            ballDirX = 0;
            ballDirY = 0;
            isPlay = false;
            g.setColor(Color.cyan);
            g.drawString("You WIN! Scores: " + score, 215, 300);
            g.drawString("Press Enter To Restart", 215, 350);
        }

        if (ballPosY >= 550) {
            ballDirX = 0;
            ballDirY = 0;
            isPlay = false;
            g.setColor(Color.cyan);
            g.drawString("GAME OVER! Scores: " + score, 215, 300);
            g.drawString("Press Enter To Restart", 215, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (isPlay) {
            Rectangle ball = new Rectangle(ballPosX, ballPosY, 20, 20);
            Rectangle paddle = new Rectangle(playerX, 550, 100, 8);
            if (ball.intersects(paddle)) {
                ballDirY = -ballDirY;
                if (ballDirX > -5 && ballDirY > -5) {
                    ballDirY = ballDirY - 1;//changing speed
                    ballDirX = ballDirX - 1;//changing speed
                }
            }

            for (int i = 0; i < mapGenerator.getCountOfRows(); i++) {
                for (int j = 0; j < mapGenerator.getCountOfColumns(); j++) {
                    if (mapGenerator.getElementOfMap(i, j) > 0) {
                        int brickX = j * mapGenerator.getBrickWidth() + 80;
                        int brickY = i * mapGenerator.getBrickHeight() + 50;
                        Rectangle brick = new Rectangle(brickX, brickY, mapGenerator.getBrickWidth(), mapGenerator.getBrickHeight());
                        if (ball.intersects(brick)) {
                            if (ball.x + 19 <= brickX || ball.x + 1 >= brick.width + brick.x) {
                                ballDirX = -ballDirX;
                                mapGenerator.setBrickValue(0, i, j);
                                checkAndChangeElementOfMap(i, j);
//                                System.out.println("FROM X");
//                                System.out.println("INTERSECTS: brick x = " + brick.x + ", brick y = " + brick.y
//                                        + ", brick width = " + brick.width + ", brick height = " + brick.height);
//                                System.out.println("BALL : " + ball);
                            } else if (ball.y - 1 >= brick.y && ball.x >= brick.x + brick.width - brick.width/2 && ball.x >= brick.x - brick.width/2) {
                                ballDirY = -ballDirY;
                                mapGenerator.setBrickValue(0, i, j);
                                checkAndChangeElementOfMap(i, j);
                            } else if (ball.y + ball.height - 1 >= brick.y && ball.x >= brick.x + brick.width - brick.width/2 && ball.x >= brick.x - brick.width/2) {
                                ballDirY = -ballDirY;
                                mapGenerator.setBrickValue(0, i, j);
                                checkAndChangeElementOfMap(i, j);
                            } else {
                                ballDirY = -ballDirY;
                                mapGenerator.setBrickValue(0, i, j);
                                totalBricks--;
                                score++;
//                                System.out.println("FROM Y");
//                                System.out.println("INTERSECTS: brick x = " + brick.x + ", brick y = " + brick.y
//                                        + ", brick width = " + brick.width + ", brick height = " + brick.height);
//                                System.out.println("BALL : " + ball);
                            }
                            System.out.println(totalBricks);
                        }
                    }
                }
            }

            ballPosX = ballPosX + ballDirX;
            ballPosY = ballPosY + ballDirY;

            if (ballPosX >= 775) {
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!isPlay) {
                isPlay = true;
                totalBricks = row * col;
                score = 0;
                playerX = 310;
                ballDirX = -1;
                ballDirY = -2;
                ballPosY = 350;
                ballPosX = 120;
                mapGenerator = new MapGenerator(row, col);

                repaint();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            isTest = true;
            isPlay = true;
            totalBricks = row * col;
            score = 0;
            playerX = 310;
            ballDirX = -1;
            ballDirY = -2;
            ballPosY = 350;
            ballPosX = 120;
            mapGenerator = new MapGenerator(row, col);

            repaint();
        } else if (isPlay == false) {
            return;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
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

    private void checkAndChangeElementOfMap(int i, int j) {
        if (j + 1 < mapGenerator.getCountOfColumns()) {
            if (mapGenerator.getElementOfMap(i, j+1) > 0) {
                mapGenerator.setBrickValue(0, i, j + 1);
                totalBricks = totalBricks - 2;
                score = score + 2;
            } else {
                totalBricks--;
                score++;
            }
        }
    }
}
