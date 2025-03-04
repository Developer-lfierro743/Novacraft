package com.novaforgestudios.novacraft.core;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f position; // Camera position in 3D space
    private Vector3f forward;  // Forward direction
    private Vector3f up;       // Up direction

    private float yaw = -90.0f;   // Horizontal angle (in degrees)
    private float pitch = 0.0f;   // Vertical angle (in degrees)

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
     * Rotates the camera based on mouse movement.
     *
     * @param xOffset Change in horizontal mouse movement.
     * @param yOffset Change in vertical mouse movement.
     */
    public void rotate(float xOffset, float yOffset) {
        float sensitivity = 0.1f;

        // Update yaw and pitch
        yaw += xOffset * sensitivity;
        pitch += yOffset * sensitivity;

        // Clamp pitch to prevent flipping
        if (pitch > 89.0f) pitch = 89.0f;
        if (pitch < -89.0f) pitch = -89.0f;

        // Recalculate the forward vector based on yaw and pitch
        updateForwardVector();
    }

    /**
     * Updates the forward vector based on yaw and pitch.
     */
    private void updateForwardVector() {
        float yawRad = (float) Math.toRadians(yaw);
        float pitchRad = (float) Math.toRadians(pitch);

        forward.x = (float) (Math.cos(yawRad) * Math.cos(pitchRad));
        forward.y = (float) Math.sin(pitchRad);
        forward.z = (float) (Math.sin(yawRad) * Math.cos(pitchRad));
        forward.normalize();
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

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}