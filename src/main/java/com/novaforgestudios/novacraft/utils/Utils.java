package com.novaforgestudios.novacraft.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Utils {
    /**
     * Loads a resource file into a ByteBuffer.
     *
     * @param resourcePath The path to the resource file.
     * @param bufferSize   The size of the buffer to allocate.
     * @return A ByteBuffer containing the resource data.
     */
    public static ByteBuffer ioResourceToByteBuffer(String resourcePath, int bufferSize) {
        ByteBuffer buffer = null;

        try (InputStream source = Utils.class.getResourceAsStream(resourcePath);
             ReadableByteChannel channel = Channels.newChannel(source)) {

            if (source == null) {
                throw new IOException("Could not find resource: " + resourcePath);
            }

            buffer = ByteBuffer.allocateDirect(bufferSize);
            while (channel.read(buffer) != -1) {
                // Expand buffer if necessary
                if (buffer.remaining() == 0) {
                    ByteBuffer newBuffer = ByteBuffer.allocateDirect(buffer.capacity() * 2);
                    buffer.flip();
                    newBuffer.put(buffer);
                    buffer = newBuffer;
                }
            }
            buffer.flip();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource: " + resourcePath, e);
        }

        return buffer;
    }
}