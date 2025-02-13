package com.github.georgi4511.victbot.scripts.clean_command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.util.Objects;

public class CommandUtils {
    CommandUtils() {
        throw new UnsupportedOperationException();
    }

    public static void cleanCommands(String token) throws InterruptedException {
        JDA jda = JDABuilder.createDefault(token).build();
        jda.awaitReady();
        jda.updateCommands().complete();
        jda.shutdown();
    }

    public static void cleanCommands(String token, String guild) throws InterruptedException {
        JDA jda = JDABuilder.createDefault(token).build();
        jda.awaitReady();
        Objects.requireNonNull(jda.getGuildById(guild)).updateCommands().complete();
        jda.shutdown();
    }
}

