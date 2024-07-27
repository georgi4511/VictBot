package com.github.georgi4511.discord_bot.jsonService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class BaseJSON {
    private final static String JSON_FOLDER ="jsons";
    String name;
    String content;

    BaseJSON(){
        throw new IllegalCallerException();
    }
    BaseJSON(String name,String content) throws IOException {
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
