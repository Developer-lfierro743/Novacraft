package com.novaforgestudios.novacraft.world;

public class PerlinNoise {

    // Permutation table for pseudo-random gradients
    private static final int[] permutation = {
        151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225,
        140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148,
        247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32,
        57, 177, 33, 88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175,
        74, 165, 71, 134, 139, 48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122,
        60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54,
        65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169,
        200, 196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64,
        52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126, 255, 82, 85, 212,
        207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 223, 183, 170, 213,
        119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
        129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104,
        218, 246, 97, 228, 251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241,
        81, 51, 145, 235, 249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157,
        184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93,
        222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180
    };

    // Fade function (smoothstep)
    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    // Linear interpolation
    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    // Gradient function
    private static double grad(int hash, double x, double y) {
        int h = hash & 15; // Convert low 4 bits of hash into 12 gradient directions
        double u = h < 8 ? x : y;
        double v = h < 4 ? y : (h == 12 || h == 14 ? x : 0);
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    /**
     * Generates a Perlin noise value for the given (x, y) coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return A noise value in the range [0, 1].
     */
    public static double perlin(double x, double y) {
        int X = (int) Math.floor(x) & 255; // Find unit grid cell containing point
        int Y = (int) Math.floor(y) & 255;

        double xf = x - Math.floor(x); // Fractional part of x and y
        double yf = y - Math.floor(y);

        double u = fade(xf); // Compute fade curves for each of xf and yf
        double v = fade(yf);

        int A = permutation[X] + Y;
        int AA = permutation[A];
        int AB = permutation[A + 1]; // Hashes for corners
        int B = permutation[X + 1] + Y;
        int BA = permutation[B];
        int BB = permutation[B + 1];

        // Blend contributions from 4 corners of the unit square
        double result = lerp(v, lerp(u, grad(permutation[AA], xf, yf),
                                        grad(permutation[BA], xf - 1, yf)),
                                lerp(u, grad(permutation[AB], xf, yf - 1),
                                        grad(permutation[BB], xf - 1, yf - 1)));

        return (result + 1) / 2; // Normalize to [0, 1]
    }
}