package com.novaforgestudios.novacraft.rendering;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Cube {

private int vaoId;
 private int vboId;
private int eboId;

public Cube() {
    init();
 }

 private void init() {
     float[] vertices = {
     // Front face (red)
     -0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
     0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
     0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
     -0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
     // Back face (green)
     -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
     0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
     0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
     -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
     // Left face (blue)
     -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
     -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
     -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
     -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 1.0f,
     // Right face (yellow)
     0.5f, -0.5f, 0.5f, 1.0f, 1.0f, 0.0f,
     0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f,
     0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.0f,
     0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.0f,
     // Top face (cyan)
     -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 1.0f,
     0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 1.0f,
     0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 1.0f,
     -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 1.0f,
     // Bottom face (magenta)
     -0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 1.0f,
     0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 1.0f,
     0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 1.0f,
     -0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 1.0f,
     };
     int[] indices = {
     // front face
     0, 1, 2,
     2, 3, 0,
     // back face
     4, 5, 6,
     6, 7, 4,
     // left face
     0, 3, 7,
     7, 4, 0,
     // right face
     1, 2, 6,
     6, 5, 1,
     // top face
     3, 2, 6,
     6, 7, 3,
     // bottom face
     0, 1, 5,
     5, 4, 0
     };

     vaoId = GL30.glGenVertexArrays();
     GL30.glBindVertexArray(vaoId);

     vboId = GL15.glGenBuffers();
     GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
     FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
     vertexBuffer.put(vertices).flip();
     GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);

     GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 6 * Float.BYTES, 0); // Position
     GL20.glEnableVertexAttribArray(0);

     GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES); // Color
     GL20.glEnableVertexAttribArray(1);

     eboId = GL15.glGenBuffers();
     GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, eboId);
     IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
     indicesBuffer.put(indices).flip();
     GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);

     GL30.glBindVertexArray(0);
     GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
     GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

     // memFree(vertexBuffer); // Removed
     // memFree(indicesBuffer); // Removed
     }

     public void render() {
     GL30.glBindVertexArray(vaoId);
     GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, eboId);
     GL11.glDrawElements(GL11.GL_TRIANGLES, 36, GL11.GL_UNSIGNED_INT, 0);
     GL30.glBindVertexArray(0);
     }

     public void cleanup() {
     GL15.glDeleteBuffers(vboId);
     GL15.glDeleteBuffers(eboId);
     GL30.glDeleteVertexArrays(vaoId);
     }
 }
