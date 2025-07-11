package com.github.georgi4511.victbot.command;

import java.util.Objects;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractVictCommand implements VictCommand {

  public static final String FAIL_TO_COMPLETE_MESSAGE = "Failed to complete command";
  private static final Logger log = LoggerFactory.getLogger(AbstractVictCommand.class);

  private static void logCommandExecution(SlashCommandInteractionEvent event) {
    String userName = event.getUser().getId();
    String guildName =
        event.isFromGuild() ? Objects.requireNonNull(event.getGuild()).getId() : null;
    String eventName = event.getName();
    log.info(
        "Command '{}' executed by User with Id '{}' of Guild with Id '{}'",
        eventName,
        userName,
        guildName);
  }

  @Override
  public void handleSelectInteraction(StringSelectInteractionEvent event) {
    throw new UnsupportedOperationException(
        "Abstract class does not not contain data, please implement functionality wherever needed");
  }

  @Override
  public String getName() {
    return getData().getName();
  }

  protected abstract void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent);

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    try {
      logCommandExecution(event);

      handleSlashCommandInteraction(event);
    } catch (Exception e) {
      if (Boolean.TRUE.equals(isDeferred())) {
        event.getHook().editOriginal(FAIL_TO_COMPLETE_MESSAGE).queue();
      }
      log.error("Command to execute: {}", e.getMessage(), e);
    } finally {
      if (!event.isAcknowledged()) {
        event.reply(FAIL_TO_COMPLETE_MESSAGE).queue();
      }
    }
  }

  @Override
  public Long getCooldown() {
    return 5L;
  }

  @Override
  public Boolean isDeferred() {
    return false;
  }

  @Override
  public Boolean isDevCommand() {
    return false;
  }
}
