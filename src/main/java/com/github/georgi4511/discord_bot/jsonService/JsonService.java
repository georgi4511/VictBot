package com.github.georgi4511.discord_bot.jsonService;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class JsonService {

    public static BotImpressionsJSON botImpressionsJSON;
    public static MessageReactionsJSON[] messageReactionsJSONArray;
    public static ReminderJSON[] reminderJSONArray;
    public static final String JSON_FOLDER = "jsons";

    public static void instantiateJsons() throws IOException {
        Path basedir = Paths.get(".");
        if (!basedir.toFile().isDirectory()) throw new IOException();
        boolean directoryExists = Arrays.stream(Objects.requireNonNull(basedir.toFile().listFiles())).anyMatch(f->f.isDirectory()&& f.getName().equals(JSON_FOLDER));

        if(!directoryExists) {
            Files.createDirectory(Path.of(JSON_FOLDER));
        }

        new BotImpressionsJSON();
        new MessageReactionsJSON();
        new ReminderJSON();

        String botImpressionsResult = Files.readString(Path.of(JSON_FOLDER, "botImpressions" + ".json"));
        botImpressionsJSON = new Gson().fromJson(botImpressionsResult,BotImpressionsJSON.class);
String reminderResult = Files.readString(Path.of(JSON_FOLDER, "reminder" + ".json"));
        reminderJSONArray = new Gson().fromJson(reminderResult,ReminderJSON[].class);
String messageReactionsResult = Files.readString(Path.of(JSON_FOLDER, "messageReactions" + ".json"));
        messageReactionsJSONArray = new Gson().fromJson(messageReactionsResult,MessageReactionsJSON[].class);
    }


}
