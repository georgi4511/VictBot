package com.github.georgi4511.victbot.model;

import lombok.Data;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@Data
public abstract class VictCommand {
  private SlashCommandData data;
  private String name;
  private Long cooldown = 5L;
  private Permission[] permissions;
  private Boolean devCommand;

  public abstract void callback(SlashCommandInteractionEvent event);

  public void handleSelectInteraction(StringSelectInteractionEvent event) {
    event.reply("NOT IMPLEMENTED").setEphemeral(true).queue();
  }
}
