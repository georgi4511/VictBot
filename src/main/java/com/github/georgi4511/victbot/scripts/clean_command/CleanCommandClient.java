package com.github.georgi4511.victbot.scripts.clean_command;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MissingRequiredPropertiesException;

import static com.github.georgi4511.victbot.scripts.clean_command.CommandUtils.cleanCommands;
import static java.util.Objects.isNull;

public class CleanCommandClient {

    private static final Logger log = LoggerFactory.getLogger(CleanCommandClient.class);

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
            log.error("Command deletion interrupted {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}

