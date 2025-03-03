package com.novaforgestudios.novacraft.core;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f position; // Camera position in 3D space
    private Vector3f forward;  // Forward direction
    private Vector3f up;       // Up direction

    public Camera() {
        position = new Vector3f(0, 0, 3); // Start slightly back from the origin
        forward = new Vector3f(0, 0, -1); // Look down the -Z axis
        up = new Vector3f(0, 1, 0);       // Y-axis is "up"
    }

    /**
     * Moves the camera based on input.
     *
     * @param dx Change in the X direction (left/right).
     * @param dy Change in the Y direction (up/down).
     * @param dz Change in the Z direction (forward/backward).
     */
    public void move(float dx, float dy, float dz) {
        // Calculate movement vectors
        Vector3f forwardMovement = new Vector3f(forward).mul(dz);
        Vector3f sideMovement = new Vector3f(forward).cross(up).normalize().mul(dx);

        // Update position
        position.add(forwardMovement);
        position.add(sideMovement);
        position.y += dy; // Adjust height
    }

    /**
     * Gets the view matrix for the camera.
     *
     * @return The view matrix.
     */
    public Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(
            position,
            new Vector3f(position).add(forward), // Target point (position + forward)
            up
        );
    }

    public Vector3f getPosition() {
        return position;
    }
}