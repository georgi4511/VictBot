package com.github.georgi4511.victbot.scripts.clean_command;

import static com.github.georgi4511.victbot.scripts.clean_command.CommandUtils.cleanCommands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MissingRequiredPropertiesException;

class CleanCommandClient {

  private static final Logger log = LoggerFactory.getLogger(CleanCommandClient.class);

  private CleanCommandClient() {
    throw new UnsupportedOperationException("Helper class should not be instantiated");
  }

  public static void main() {
    String botToken = System.getenv("BOT_TOKEN");
    String botGuild = System.getenv("BOT_GUILD");

    if (botToken == null) {
      throw new MissingRequiredPropertiesException();
    }
    try {
      if (botGuild == null) {
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
