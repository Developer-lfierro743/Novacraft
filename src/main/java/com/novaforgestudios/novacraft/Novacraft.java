package com.novaforgestudios.novacraft;

import com.novaforgestudios.novacraft.Engine.Game;
import com.novaforgestudios.novacraft.core.Display;
import com.novaforgestudios.novacraft.core.SharedConstants;

public class Novacraft {

    public static void main(String[] args) {
        Game game = new Game();
        Display display = new Display(SharedConstants.GAME_WIDTH, SharedConstants.GAME_HEIGHT, SharedConstants.GAME_TITLE, game);
        game.setDisplay(display);
        display.start();
    }
}