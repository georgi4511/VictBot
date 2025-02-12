package com.github.georgi4511.victbot.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseJson {
    public static final String JSON_EXT = ".json";
    private static final String JSON_FOLDER = "jsons";
    String name;
    String content;

    BaseJson(String name, String content) throws IOException {
        this.name = name;
        this.content = content;
        createJsonFile(name, content);

    }

    private static void createJsonFile(String name, String contents) throws IOException {
        Path dir = Paths.get(".", JSON_FOLDER, name + JSON_EXT);
        boolean jsonFileExists = dir.toFile().isFile() && dir.toFile().getName().equals(name + JSON_EXT);

        if (!jsonFileExists) {
            Files.write(Path.of(JSON_FOLDER, name + JSON_EXT), contents.getBytes());
        }
    }
}
