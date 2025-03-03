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

    // Player size (scaled down to 0.8x0.8x0.8)
    private static final float PLAYER_WIDTH = 0.8f;
    private static final float PLAYER_HEIGHT = 0.8f;

    public Player() {
        position = new Vector3f(0, 0.5f, 0); // Start on top of the cube's cyan face (y = 0.5)
        cube = new Cube();                  // Use a cube to represent the player
    }

    /**
     * Moves the player in the specified direction, checking for collisions.
     *
     * @param dx Change in the X direction (left/right).
     * @param dy Change in the Y direction (up/down).
     * @param dz Change in the Z direction (forward/backward).
     */
    public void move(float dx, float dy, float dz, Cube otherCube) {
        // Temporarily move the player
        position.add(dx, 0, dz);

        // Check for collisions with the other cube
        if (checkCollision(otherCube)) {
            // Revert the movement if a collision is detected
            position.sub(dx, 0, dz);
        }

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
     * Checks for collisions between the player and another cube.
     *
     * @param otherCube The other cube to check for collisions.
     * @return True if there is a collision, false otherwise.
     */
    private boolean checkCollision(Cube otherCube) {
        // Define the player's bounding box
        float playerMinX = position.x - PLAYER_WIDTH / 2;
        float playerMaxX = position.x + PLAYER_WIDTH / 2;
        float playerMinY = position.y - PLAYER_HEIGHT / 2;
        float playerMaxY = position.y + PLAYER_HEIGHT / 2;
        float playerMinZ = position.z - PLAYER_WIDTH / 2;
        float playerMaxZ = position.z + PLAYER_WIDTH / 2;

        // Define the other cube's bounding box (assuming it's centered at (0, 0, 0) with size 1x1x1)
        float cubeMinX = -0.5f;
        float cubeMaxX = 0.5f;
        float cubeMinY = -0.5f;
        float cubeMaxY = 0.5f;
        float cubeMinZ = -0.5f;
        float cubeMaxZ = 0.5f;

        // Check for overlap on all axes
        return !(playerMaxX < cubeMinX || playerMinX > cubeMaxX ||
                 playerMaxY < cubeMinY || playerMinY > cubeMaxY ||
                 playerMaxZ < cubeMinZ || playerMinZ > cubeMaxZ);
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