package com.novaforgestudios.novacraft.utils;

public class PlatformUtils {
    /**
     * Checks if the application is running on a native Android environment.
     *
     * @return True if running on Android, false otherwise.
     */
    public static boolean isAndroid() {
        String osName = System.getProperty("os.name").toLowerCase();
        // Check for Android-specific environment variables
        return osName.contains("linux") && System.getenv("ANDROID_ROOT") != null && System.getenv("PREFIX") == null;
    }

    /**
     * Checks if the application is running in a Termux environment with proot-distro.
     *
     * @return True if running in Termux with proot-distro, false otherwise.
     */
    public static boolean isTermuxProot() {
        String osName = System.getProperty("os.name").toLowerCase();
        // Check for Termux-specific environment variables
        return osName.contains("linux") && System.getenv("PREFIX") != null;
    }
}