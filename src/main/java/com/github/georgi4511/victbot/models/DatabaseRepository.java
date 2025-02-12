package com.github.georgi4511.victbot.models;

import com.google.gson.Gson;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
public class DatabaseRepository {

    private static final Logger log = LoggerFactory.getLogger(DatabaseRepository.class);
    private static final Path path = Paths.get("", "jsons", "database.json");
    private static final Gson gson = new Gson();
    private Database database;

    public DatabaseRepository() {
        database = initializedatabase();
    }

    public static Database initializedatabase() {

        File jsonFolder = path.getParent().toFile();

        try {
            if (path.toFile().exists() && path.toFile().isFile()) {
                String databaseS = Files.readString(path);
                return gson.fromJson(databaseS, Database.class);
            }
            if (!jsonFolder.exists() || !jsonFolder.isDirectory()) {
                boolean mkdir = jsonFolder.mkdir();
                assert mkdir;
            }
            Database database = new Database();
            Files.write(path, gson.toJson(database).getBytes());
            return database;
        } catch (IOException e) {
            log.error("Failed to initialize Database json: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void save() throws IOException {
        Files.write(path, gson.toJson(database, Database.class).getBytes());
    }
}




