package com.game.ez;

import javax.swing.*;

/**
 * Created by Arizel on 24.07.2017.
 */
public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setBounds( 10, 10,800, 600);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jFrame.add(new GamePlay(7, 12));
    }
}
