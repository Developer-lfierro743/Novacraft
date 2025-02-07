package com.novaforgestudios.novacraft.core;

import java.awt.Canvas;
import javax.swing.JFrame;

public class Display extends Canvas {

    int WIDTH = 800;
    int HEIGHT = 600;

    public static void main(String[] args) {
        Display game = new Display();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(game.WIDTH, game.HEIGHT);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}