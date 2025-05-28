package com.github.georgi4511.victbot.model;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractVictCommand implements VictCommand {

  private static final Logger log = LoggerFactory.getLogger(AbstractVictCommand.class);

  @Override
  public void handleSelectInteraction(StringSelectInteractionEvent event) {
    throw new UnsupportedOperationException(
        "Abstract class does not not contain data, please implement functionality wherever needed");
  }

  @Override
  public String getName() {
    return getData().getName();
  }

  @Override
  public void executeCallback(SlashCommandInteractionEvent event) {
    try {
      callback(event);
    } catch (Exception e) {
      if (Boolean.TRUE.equals(getIsDeferred())) {
        event.getHook().deleteOriginal().queue();
        log.error("Deferred reply command failed threw exception");
      }
      log.error("Command to execute: {}", e.getMessage(), e);
    } finally {

      if (!event.isAcknowledged()) {
        event.reply("Command failed to complete").queue();
      }
    }
  }

  @Override
  public Long getCooldown() {
    return 5L;
  }

  @Override
  public Boolean getIsDeferred() {
    return false;
  }

  @Override
  public Boolean getDevCommand() {
    return false;
  }
}
