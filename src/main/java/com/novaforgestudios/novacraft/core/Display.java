package com.novaforgestudios.novacraft.core;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Display extends Canvas {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    public static final String TITLE = "Novacraft Pre-Development Version";

    public Display() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public static void main(String[] args) {
        Display game = new Display();
        JFrame frame = new JFrame(TITLE);

        // Configure the JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void start() {
        System.out.println("Game started!");
    }
}