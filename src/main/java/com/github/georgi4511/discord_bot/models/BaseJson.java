package com.github.georgi4511.discord_bot.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseJson {
    private final static String JSON_FOLDER ="jsons";
    String name;
    String content;

    BaseJson(){
        throw new IllegalCallerException();
    }
    BaseJson(String name, String content) throws IOException {
        this.name=name;
        this.content=content;
        createJsonFile(name,content);

    }

    private static void createJsonFile(String name,String contents) throws IOException {
        Path dir = Paths.get(".",JSON_FOLDER,name+".json");
        boolean jsonFileExists = dir.toFile().isFile() && dir.toFile().getName().equals(name+".json");

        if(!jsonFileExists) {
            Files.write(Path.of(JSON_FOLDER,name+".json"), contents.getBytes());
        }
    }
}
