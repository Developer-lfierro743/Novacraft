package com.novaforgestudios.novacraft.rendering;

import org.lwjgl.opengl.GL20;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class Shader {

    private int id;

    public Shader(String vertexShaderSource, String fragmentShaderSource) {
        id = createShaderProgram(vertexShaderSource, fragmentShaderSource);
    }

    private int createShaderProgram(String vertexShaderSource, String fragmentShaderSource) {
        int vertexShader = compileShader(GL20.GL_VERTEX_SHADER, vertexShaderSource);
        int fragmentShader = compileShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderSource);

        int program = GL20.glCreateProgram();
        GL20.glAttachShader(program, vertexShader);
        GL20.glAttachShader(program, fragmentShader);
        GL20.glLinkProgram(program);

        
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);

        
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
            System.err.println("Shader program linking failed: " + GL20.glGetProgramInfoLog(program));
            return -1;
        }

        return program;
    }

    private int compileShader(int type, String source) {
        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, source);
        GL20.glCompileShader(shader);

        // Check for compilation errors
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
            System.err.println("Shader compilation failed: " + GL20.glGetShaderInfoLog(shader));
            return -1;
        }
        return shader;
    }

    public void bind() {
        GL20.glUseProgram(id);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public int getId() {
        return id;
    }

    public void cleanup() {
        unbind();
        GL20.glDeleteProgram(id);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        int uniformLocation = GL20.glGetUniformLocation(id, uniformName);

        if (uniformLocation == -1) {
            System.err.println("Warning: Uniform '" + uniformName + "' not found in shader.");
            return;
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16); // 4x4 matrix = 16 floats
            value.get(fb);
            GL20.glUniformMatrix4fv(uniformLocation, false, fb);
        }
    }
}
