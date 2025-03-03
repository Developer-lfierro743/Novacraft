package com.novaforgestudios.novacraft.core;

import org.


import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LocalizationManager {
    private static Map<String, String> translations = new HashMap<>();
    private static String languageName;

    // Load translations dynamically
    public static void loadTranslations(String languageCode) {
        try {
            String fileName = languageCode + ".json";
            InputStream inputStream = LocalizationManager.class.getClassLoader().getResourceAsStream(fileName);

            if (inputStream == null) {
                throw new IllegalArgumentException("Language file not found: " + fileName);
            }

            Scanner scanner = new Scanner(inputStream).useDelimiter("\b");
            String jsonString = scanner.hasNext() ? scanner.next() : "";
            scanner.close();

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonString);
            parseJsonObject(jsonObject, "");
            languageName = ((JSONObject) jsonObject.get("language")).get("name").toString();

        } catch (Exception e) {
            System.err.println("Error loading language file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Parse the JSON object and store key-value pairs
    private static void parseJsonObject(JSONObject jsonObject, String prefix) {
        for (Object keyObj : jsonObject.keySet()) {
            String key = (String) keyObj;
            if (key.equals("language"))
                continue;
            Object value = jsonObject.get(key);
            String fullKey = prefix.isEmpty() ? key : prefix + "." + key;

            if (value instanceof JSONObject) {
                parseJsonObject((JSONObject) value, fullKey);
            } else {
                translations.put(fullKey, value.toString());
            }
        }
    }

    // Get translation for a key, with optional dynamic arguments
    public static String getTranslation(String key, Object... args) {
        String translation = translations.get(key);
        if (translation == null) {
            System.err.println("Translation not found for key: " + key);
            return key;
        }

        return String.format(translation, args);
    }

    // Get the name of the language
    public static String getLanguageName() {
        return languageName;
    }

    // Add or modify a dynamic translation at runtime
    public static void addDynamicTranslation(String key, String value) {
        translations.put(key, value);
    }

    // Save modified translations back to the JSON file
    public static void saveTranslations(String languageCode) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("language", new JSONObject() {
                {
                    put("name", languageName);
                }
            });

            // Add all translations to the JSON object
            for (Map.Entry<String, String> entry : translations.entrySet()) {
                String[] keys = entry.getKey().split("\\.");
                JSONObject current = jsonObject;

                for (int i = 0; i < keys.length - 1; i++) {
                    current = (JSONObject) current.computeIfAbsent(keys[i], k -> new JSONObject());
                }
                current.put(keys[keys.length - 1], entry.getValue());
            }

            // Write the updated JSON back to the file
            OutputStream outputStream = Files.newOutputStream(Paths.get(languageCode + ".json"));
            outputStream.write(jsonObject.toJSONString().getBytes());
            outputStream.close();

        } catch (Exception e) {
            System.err.println("Error saving translations: " + e.getMessage());
            e.printStackTrace();
        }
    }
}