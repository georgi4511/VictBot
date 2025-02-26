package com.github.georgi4511.victbot.model;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface VictCommand {
  void callback(SlashCommandInteractionEvent event);

  default void handleSelectInteraction(StringSelectInteractionEvent event) {
    event.reply("Not implemented").queue();
  }

  SlashCommandData getData();

  default String getName() {
    return getData().getName();
  }

  default Long getCooldown() {
    return 5L;
  }

  default Boolean getDevCommand() {
    return false;
  }
}
