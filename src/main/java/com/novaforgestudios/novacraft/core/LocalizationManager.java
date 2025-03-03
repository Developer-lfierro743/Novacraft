package com.novaforgestudios.novacraft.core;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class LocalizationManager {
    private static final Map<String, String> translations = new HashMap<>();
    private static String languageName;

    /**
     * Load translations dynamically from a JSON file.
     *
     * @param languageCode The language code (e.g., "en", "fr").
     */
    public static void loadTranslations(String languageCode) {
        if (!languageCode.matches("[a-z]{2}")) {
            throw new IllegalArgumentException("Invalid language code: " + languageCode);
        }

        String fileName = languageCode + ".json";

        try (InputStream inputStream = LocalizationManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Language file not found: " + fileName);
            }

            // Read the entire content of the input stream as a string
            String jsonString;
            try (var scanner = new java.util.Scanner(inputStream).useDelimiter("\\A")) {
                jsonString = scanner.hasNext() ? scanner.next() : "";
            }

            // Parse JSON content
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

            // Parse the JSON object into key-value pairs
            parseJsonObject(jsonObject, "");

            // Extract the language name
            JsonElement languageObj = jsonObject.get("language");
            if (languageObj != null && languageObj.isJsonObject()) {
                JsonElement nameObj = languageObj.getAsJsonObject().get("name");
                if (nameObj != null) {
                    languageName = nameObj.getAsString();
                } else {
                    throw new IllegalArgumentException("Missing 'name' field in 'language' object.");
                }
            } else {
                throw new IllegalArgumentException("Invalid or missing 'language' object in JSON.");
            }

        } catch (Exception e) {
            System.err.println("Error loading language file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Parse the JSON object recursively and store key-value pairs in the translations map.
     *
     * @param jsonObject The JSON object to parse.
     * @param prefix     The current key prefix for nested objects.
     */
    private static void parseJsonObject(JsonObject jsonObject, String prefix) {
        for (String key : jsonObject.keySet()) {
            // Skip the "language" key since it's handled separately
            if (key.equals("language")) {
                continue;
            }

            JsonElement value = jsonObject.get(key);
            String fullKey = prefix.isEmpty() ? key : prefix + "." + key;

            if (value.isJsonObject()) {
                parseJsonObject(value.getAsJsonObject(), fullKey);
            } else {
                translations.put(fullKey, value.getAsString());
            }
        }
    }

    /**
     * Get the translation for a given key, with optional dynamic arguments.
     *
     * @param key  The translation key.
     * @param args Optional arguments for formatting.
     * @return The translated string or the key itself if no translation is found.
     */
    public static String getTranslation(String key, Object... args) {
        String translation = translations.get(key);
        if (translation == null) {
            System.err.println("Translation not found for key: " + key);
            return key; // Fallback to the key itself
        }

        try {
            return String.format(translation, args);
        } catch (Exception e) {
            System.err.println("Error formatting translation for key: " + key);
            return translation; // Return untranslated string as fallback
        }
    }

    /**
     * Get the name of the currently loaded language.
     *
     * @return The language name.
     */
    public static String getLanguageName() {
        return languageName;
    }

    /**
     * Add or modify a translation at runtime.
     *
     * @param key   The translation key.
     * @param value The translation value.
     */
    public static void addDynamicTranslation(String key, String value) {
        translations.put(key, value);
    }

    /**
     * Save modified translations back to the JSON file.
     *
     * @param languageCode The language code (e.g., "en", "fr").
     */
    public static void saveTranslations(String languageCode) {
        try (OutputStream outputStream = Files.newOutputStream(Paths.get(languageCode + ".json"))) {
            JsonObject jsonObject = new JsonObject();
            JsonObject languageObject = new JsonObject();
            languageObject.addProperty("name", languageName);
            jsonObject.add("language", languageObject);

            // Add all translations to the JSON object
            for (Map.Entry<String, String> entry : translations.entrySet()) {
                String[] keys = entry.getKey().split("\\.");
                JsonObject current = jsonObject;

                for (int i = 0; i < keys.length - 1; i++) {
                    current = current.getAsJsonObject(keys[i]);
                    if (current == null) {
                        current = new JsonObject();
                        jsonObject.add(keys[i], current);
                    }
                }
                current.addProperty(keys[keys.length - 1], entry.getValue());
            }

            // Write the updated JSON back to the file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            outputStream.write(gson.toJson(jsonObject).getBytes());

        } catch (Exception e) {
            System.err.println("Error saving translations: " + e.getMessage());
            e.printStackTrace();
        }
    }
}