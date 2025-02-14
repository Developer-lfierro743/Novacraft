/*
 * Novacraft - A voxel-based game inspired by Minecraft
 * Copyright (C) 2024-2025 NovaForge Studios
 * 
 * This file contains the core game code for Novacraft. This code is
 * crucial for the functionality of the game and should not be modified.
 * Any modifications to this code may cause the game to become unstable,
 * or to not work at all. Furthermore, modifying this code may be a
 * violation of the license under which Novacraft is distributed.
 * 
 * To protect the intellectual property of NovaForge Studios, this code
 * is not exposed to the outside public and any reverse engineering,
 * decompilation, disassembly, or other attempts to discover the source
 * code of this game is strictly forbidden.
 * 
 * If you are a developer and wish to contribute to Novacraft, please
 * contact us at [developer@novacraft.com](mailto:developer@novacraft.com) to
 * discuss ways in which you can help.
 */

package com.novaforgestudios.novacraft.core;

public class Game {
    public static void main(String[] args){
        System.out.println("Novacraft is starting up...");
        new Game().start();
    }
    
    /**
     * Initializes the game loop.
     * <p>
     * This method is responsible for starting the game loop. The game loop
     * is responsible for updating the game state and rendering the game.
     * <p>
     * This method does nothing currently, and is a placeholder for the
     * game loop that will be implemented later.
     */
    public void start(){
        System.out.println("Game loop initialized.");
        // TODO: Implement game loop
        }
}
