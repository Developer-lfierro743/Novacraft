package com.novaforgestudios.novacraft.core;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Display extends Canvas {

    public Display() {
        setPreferredSize(new Dimension(SharedConstants.GAME_WIDTH, SharedConstants.GAME_HEIGHT));
    }

    public static void main(String[] args) {
        Display game = new Display();
        JFrame frame = new JFrame(SharedConstants.GAME_TITLE);

        // Configure the JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    
        System.out.println("running...");
    }
}