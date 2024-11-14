package com.github.georgi4511.discord_bot.scripts.clean_commands;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MissingRequiredPropertiesException;

import static com.github.georgi4511.discord_bot.scripts.clean_commands.CommandUtils.cleanCommands;
import static java.util.Objects.isNull;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(@NotNull String[] args) {
        String botToken = System.getenv("BOT_TOKEN");
        String botGuild = System.getenv("BOT_GUILD");

        if (isNull(botToken)) {
            throw new MissingRequiredPropertiesException();
        }
        try {
            if (isNull(botGuild)) {
                cleanCommands(botToken);
            } else {
                cleanCommands(botToken, botGuild);
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}

