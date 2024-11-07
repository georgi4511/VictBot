package com.github.georgi4511.discord_bot.services;

import com.github.georgi4511.discord_bot.models.BotImpressionsJson;
import com.github.georgi4511.discord_bot.models.MessageReactionsJson;
import com.github.georgi4511.discord_bot.models.ReminderJson;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
public class JsonService {

    private static final Logger log = LoggerFactory.getLogger(JsonService.class);
    public static BotImpressionsJson botImpressionsJSON;
    public static MessageReactionsJson[] messageReactionsJSONArray;
    public static ReminderJson[] reminderJSONArray;
    public static final String JSON_FOLDER = "jsons";

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
        String botImpressionsResult = Files.readString(Path.of(JSON_FOLDER, "botImpressions" + ".json"));
        botImpressionsJSON = new Gson().fromJson(botImpressionsResult, BotImpressionsJson.class);
String reminderResult = Files.readString(Path.of(JSON_FOLDER, "reminder" + ".json"));
        reminderJSONArray = new Gson().fromJson(reminderResult, ReminderJson[].class);
String messageReactionsResult = Files.readString(Path.of(JSON_FOLDER, "messageReactions" + ".json"));
        messageReactionsJSONArray = new Gson().fromJson(messageReactionsResult, MessageReactionsJson[].class);
            } catch (IOException e) {
                log.info(e.getMessage());
            }
    }


}
