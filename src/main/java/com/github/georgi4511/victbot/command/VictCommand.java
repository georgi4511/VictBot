package com.github.georgi4511.victbot.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface VictCommand {

  void handleSelectInteraction(StringSelectInteractionEvent event);

  SlashCommandData getData();

  void callback(SlashCommandInteractionEvent event);

  String getName();

  Long getCooldown();

  Boolean isDevCommand();

  Boolean isDeferred();
}
