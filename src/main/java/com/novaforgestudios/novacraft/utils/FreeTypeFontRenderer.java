package com.novaforgestudios.novacraft.utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;

public class FreeTypeFontRenderer {
    private final ByteBuffer fontBuffer;
    private final int textureID;
    @SuppressWarnings("unused") // Suppress unused warning for fontSize
    private final float fontSize; // Font size determines the size of baked glyphs
    private final STBTTBakedChar.Buffer charData; // Stores baked character data
    private final int bitmapWidth; // Width of the texture atlas
    private final int bitmapHeight; // Height of the texture atlas

    public FreeTypeFontRenderer(String fontPath, float fontSize) {
        this.fontSize = fontSize;

        // Load the font file into a ByteBuffer
        fontBuffer = Utils.ioResourceToByteBuffer(fontPath, 1024 * 1024);

        // Allocate STBTTFontinfo
        STBTTFontinfo fontInfo = STBTTFontinfo.create();

        // Initialize STB TrueType
        try (MemoryStack stack = MemoryStack.stackPush()) {
            if (!STBTruetype.stbtt_InitFont(fontInfo, fontBuffer, 0)) { // Pass STBTTFontinfo and offset
                throw new RuntimeException("Failed to initialize font: " + fontPath);
            }

            // Create a bitmap for the font texture
            int bitmapWidth = 512; // Width of the texture atlas
            int bitmapHeight = 512; // Height of the texture atlas
            ByteBuffer bitmap = BufferUtils.createByteBuffer(bitmapWidth * bitmapHeight);

            // Allocate space for character data
            int firstChar = 32; // Start from ASCII space (' ')
            int numChars = 96;  // Include 96 characters (space to tilde)

            // Temporary buffer for baking characters
            STBTTBakedChar.Buffer tempCharData = STBTTBakedChar.malloc(numChars); // Use malloc for persistent storage

            // Bake the font into the bitmap
            STBTruetype.stbtt_BakeFontBitmap(fontBuffer, fontSize, bitmap, bitmapWidth, bitmapHeight, firstChar, tempCharData);

            // Generate an OpenGL texture for the font
            textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_ALPHA, bitmapWidth, bitmapHeight, 0, GL11.GL_ALPHA, GL11.GL_UNSIGNED_BYTE, bitmap);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

            // Store character data for rendering
            this.charData = STBTTBakedChar.malloc(numChars);
            tempCharData.rewind(); // Reset position before copying
            this.charData.put(tempCharData);
            this.bitmapWidth = bitmapWidth;
            this.bitmapHeight = bitmapHeight;

            // Free temporary buffer
            tempCharData.free();
        }
    }

    /**
     * Renders text on the screen.
     *
     * @param text  The text to render.
     * @param x     The x-coordinate of the starting position.
     * @param y     The y-coordinate of the starting position.
     * @param scale The scaling factor for the text.
     */
    public void renderText(String text, float x, float y, float scale) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID); // Bind the font texture

        GL11.glBegin(GL11.GL_QUADS);
        for (char c : text.toCharArray()) {
            if (c < 32 || c >= 128) continue; // Skip unsupported characters

            STBTTBakedChar bakedChar = charData.get(c - 32); // Get baked character data

            // Texture coordinates
            float s0 = bakedChar.x0() / bitmapWidth;
            float t0 = bakedChar.y0() / bitmapHeight;
            float s1 = bakedChar.x1() / bitmapWidth;
            float t1 = bakedChar.y1() / bitmapHeight;

            // Vertex coordinates
            float x0 = x + bakedChar.xoff() * scale;
            float y0 = y + bakedChar.yoff() * scale;
            float x1 = x0 + (bakedChar.x1() - bakedChar.x0()) * scale;
            float y1 = y0 + (bakedChar.y1() - bakedChar.y0()) * scale;

            // Draw the quad
            GL11.glTexCoord2f(s0, t0);
            GL11.glVertex2f(x0, y0);

            GL11.glTexCoord2f(s1, t0);
            GL11.glVertex2f(x1, y0);

            GL11.glTexCoord2f(s1, t1);
            GL11.glVertex2f(x1, y1);

            GL11.glTexCoord2f(s0, t1);
            GL11.glVertex2f(x0, y1);

            // Advance the cursor
            x += bakedChar.xadvance() * scale;
        }
        GL11.glEnd();
    }

    /**
     * Cleans up resources (e.g., OpenGL textures).
     */
    public void cleanup() {
        GL11.glDeleteTextures(textureID);
        charData.free(); // Free the baked character data
    }
}