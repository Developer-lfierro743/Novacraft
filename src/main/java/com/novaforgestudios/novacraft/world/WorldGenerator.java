package com.novaforgestudios.novacraft.world;

import org.joml.SimplexNoise; // JOML's SimplexNoise class
import com.novaforgestudios.novacraft.entities.Block;

public class WorldGenerator {

    private static final int CHUNK_SIZE = 16; // Size of each chunk (16x16x16 blocks)
    private static final int WORLD_HEIGHT = 64; // Maximum height of the world
    private static final float SCALE_PERLIN = 0.05f; // Scale for Perlin Noise
    private static final float SCALE_SIMPLEX = 0.1f; // Scale for Simplex Noise

    private long seed; // Seed for noise generation

    public WorldGenerator(long seed) {
        this.seed = seed;
    }

    /**
     * Generates a chunk of blocks based on Perlin and Simplex Noise.
     *
     * @param chunkX The X-coordinate of the chunk.
     * @param chunkZ The Z-coordinate of the chunk.
     * @return A 3D array of blocks representing the chunk.
     */
    public Block[][][] generateChunk(int chunkX, int chunkZ) {
        Block[][][] blocks = new Block[CHUNK_SIZE][WORLD_HEIGHT][CHUNK_SIZE];

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                // Calculate world coordinates
                int worldX = chunkX * CHUNK_SIZE + x;
                int worldZ = chunkZ * CHUNK_SIZE + z;

                // Add seed-based offsets to simulate seeded noise
                double offsetX = (seed % 1000) / 1000.0; // Offset based on seed
                double offsetZ = ((seed >> 16) % 1000) / 1000.0;

                // Generate height using Perlin and Simplex Noise
                double perlinValue = PerlinNoise.perlin(worldX * SCALE_PERLIN, worldZ * SCALE_PERLIN);

                // Cast to float for JOML's SimplexNoise.noise method
                float simplexInputX = (float) ((worldX + offsetX) * SCALE_SIMPLEX);
                float simplexInputZ = (float) ((worldZ + offsetZ) * SCALE_SIMPLEX);
                double simplexValue = SimplexNoise.noise(simplexInputX, simplexInputZ); // Static call

                // Blend the two noises (average them)
                double height = (perlinValue + simplexValue) * 0.5;

                // Normalize height to [0, WORLD_HEIGHT]
                int terrainHeight = (int) ((height + 1) * (WORLD_HEIGHT / 2));

                // Fill blocks up to the terrain height
                for (int y = 0; y < WORLD_HEIGHT; y++) {
                    if (y <= terrainHeight) {
                        blocks[x][y][z] = new Block(Block.Type.STONE); // Stone block
                    } else {
                        blocks[x][y][z] = null; // Air
                    }
                }
            }
        }

        return blocks;
    }
}