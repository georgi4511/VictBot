package com.github.georgi4511.victbot.model;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface VictCommand {

  void callback(SlashCommandInteractionEvent event);

  void handleSelectInteraction(StringSelectInteractionEvent event);

  SlashCommandData getData();

  void executeCallback(SlashCommandInteractionEvent event);

  String getName();

  Long getCooldown();

  Boolean getDevCommand();

  Boolean getIsDeferred();
}
