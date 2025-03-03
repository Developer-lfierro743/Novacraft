package com.novaforgestudios.novacraft.entities;

import com.novaforgestudios.novacraft.rendering.Cube;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Player {

    private Vector3f position; // Player's position in the world
    private Cube cube;        // Visual representation of the player

    // Physics variables
    private float yVelocity = 0;         // Vertical velocity (for jumping/falling)
    private static final float GRAVITY = -0.02f; // Gravity strength
    private static final float JUMP_STRENGTH = 0.2f; // Jump velocity
    private boolean isOnGround = true;  // Tracks if the player is on the ground

    public Player() {
        position = new Vector3f(0, 0.5f, 0); // Start on top of the cube's cyan face (y = 0.5)
        cube = new Cube();                  // Use a cube to represent the player
    }

    /**
     * Moves the player in the specified direction.
     *
     * @param dx Change in the X direction (left/right).
     * @param dy Change in the Y direction (up/down).
     * @param dz Change in the Z direction (forward/backward).
     */
    public void move(float dx, float dy, float dz) {
        position.add(dx, 0, dz); // Move horizontally
        applyGravity();
    }

    /**
     * Makes the player jump if they are on the ground.
     */
    public void jump() {
        if (isOnGround) {
            yVelocity = JUMP_STRENGTH; // Apply upward velocity
            isOnGround = false;       // Player is no longer on the ground
        }
    }

    /**
     * Applies gravity to the player.
     */
    private void applyGravity() {
        if (!isOnGround) {
            yVelocity += GRAVITY; // Apply gravity to reduce velocity
            position.y += yVelocity; // Update vertical position

            // Check if the player has landed on the ground (y = 0.5)
            if (position.y <= 0.5f) {
                position.y = 0.5f; // Snap to the ground
                yVelocity = 0;     // Reset velocity
                isOnGround = true; // Player is back on the ground
            }
        }
    }

    /**
     * Renders the player.
     *
     * @param viewMatrix       The view matrix from the camera.
     * @param projectionMatrix The projection matrix for perspective.
     */
    public void render(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        Matrix4f modelMatrix = new Matrix4f()
            .translate(position)          // Position the player
            .scale(0.8f, 0.8f, 0.8f);     // Scale the player cube slightly smaller
        cube.getShader().setUniform("model", modelMatrix);
        cube.render(viewMatrix, projectionMatrix);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void cleanup() {
        cube.cleanup();
    }
}