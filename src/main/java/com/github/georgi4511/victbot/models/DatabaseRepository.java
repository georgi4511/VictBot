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
import java.util.List;

@Data
public class DatabaseRepository {

    private static final Logger log = LoggerFactory.getLogger(DatabaseRepository.class);
    private static final Path path = Paths.get("", "jsons", "database.json");
    private static final Gson gson = new Gson();
    private List<MessageCreateEntry> messageCreateEntryList;
    private List<ReminderEntry> reminderEntryList;
    private BotImpressions botImpressions;

    public DatabaseRepository() throws RuntimeException {

        File jsonFolder = path.getParent().toFile();

        try {
            if (path.toFile().exists() && path.toFile().isFile()) {
                String databaseS = Files.readString(path);
                DatabaseRepository databaseRepository = gson.fromJson(databaseS, DatabaseRepository.class);
                messageCreateEntryList = databaseRepository.getMessageCreateEntryList();
                reminderEntryList = databaseRepository.getReminderEntryList();
                botImpressions = databaseRepository.getBotImpressions();
                return;
            }
            if (!jsonFolder.exists() || !jsonFolder.isDirectory()) {
                boolean mkdir = jsonFolder.mkdir();
                assert mkdir;
            }
            messageCreateEntryList = List.of();
            reminderEntryList = List.of();
            botImpressions = new BotImpressions();

            Files.write(path, gson.toJson(this).getBytes());

        } catch (IOException e) {
            log.error("Failed to initialize Database json: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void save() throws IOException {
        Files.write(path, gson.toJson(this).getBytes());
    }
}




