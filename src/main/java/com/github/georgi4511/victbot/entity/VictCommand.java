/* (C)2025 */
package com.github.georgi4511.victbot.entity;

import lombok.Data;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@Data
public abstract class VictCommand {
  private SlashCommandData data;
  private String name;
  private Integer cooldown;
  private Permission[] permissions;
  private Boolean devCommand;

  public abstract void callback(SlashCommandInteractionEvent event);

  public void handleSelectInteraction(StringSelectInteractionEvent event) {
    // noop
  }
}
