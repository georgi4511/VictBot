package com.github.georgi4511.discord_bot.services;

import com.github.georgi4511.discord_bot.models.BotImpressionsJson;
import com.github.georgi4511.discord_bot.models.MessageReactionsJson;
import com.github.georgi4511.discord_bot.models.ReminderJson;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
public class JsonService {

    public static final String JSON_EXT = ".json";

    private JsonService() throws AccessDeniedException {
        throw new AccessDeniedException("Cannot instantiate helper class");
    }

    private static final Logger log = LoggerFactory.getLogger(JsonService.class);
    private static BotImpressionsJson botImpressionsJSON;
    private static MessageReactionsJson[] messageReactionsJSONArray;
    private static ReminderJson[] reminderJSONArray;
    private static final String JSON_FOLDER = "jsons";

    public static void instantiateJsons() {
            try {
        Path basedir = Paths.get(".");

        boolean directoryExists = Arrays.stream(Objects.requireNonNull(basedir.toFile().listFiles())).anyMatch(f->f.isDirectory()&& f.getName().equals(JSON_FOLDER));
        if(!directoryExists) {
                Files.createDirectory(Path.of(JSON_FOLDER));
        }

        new BotImpressionsJson();
        new MessageReactionsJson();
        new ReminderJson();
        String botImpressionsResult = Files.readString(Path.of(JSON_FOLDER, "botImpressions" + JSON_EXT));
        botImpressionsJSON = new Gson().fromJson(botImpressionsResult, BotImpressionsJson.class);
String reminderResult = Files.readString(Path.of(JSON_FOLDER, "reminder" + JSON_EXT));
        reminderJSONArray = new Gson().fromJson(reminderResult, ReminderJson[].class);
String messageReactionsResult = Files.readString(Path.of(JSON_FOLDER, "messageReactions" + JSON_EXT));
        messageReactionsJSONArray = new Gson().fromJson(messageReactionsResult, MessageReactionsJson[].class);
            } catch (IOException e) {
                log.info(e.getMessage());
            }
    }


}
