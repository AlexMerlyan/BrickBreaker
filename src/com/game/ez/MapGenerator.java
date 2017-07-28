package com.game.ez;

import java.awt.*;

/**
 * Created by Arizel on 25.07.2017.
 */
class MapGenerator {
    private int totalBricks;
    private int score;
    private int map[][];
    private int brickWidth;
    private int brickHeight;

    public MapGenerator(int row, int col) {
        map = new int[row][col];
        totalBricks = row * col;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = 1;
            }
        }

        brickWidth = 650/col;
        brickHeight = 150/row;
    }

    public void drawMap(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] > 0) {
                    g.setColor(Color.black);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 82, i * brickHeight + 52, brickWidth - 4, brickHeight - 4);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        if (map[row][col] > 0) {
            map[row][col] = value;
            totalBricks--;
            score++;
        }
    }

    public int getElementOfMap(int row, int col) {
        return map[row][col];
    }

    public int getCountOfRows() {
        return map.length;
    }

    public int getCountOfColumns() {
        return map[0].length;
    }

    public int getBrickWidth() {
        return brickWidth;
    }

    public int getBrickHeight() {
        return brickHeight;
    }

    public int getTotalBricks() {
        return totalBricks;
    }

    public int getScore() {
        return score;
    }
}
