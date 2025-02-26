package com.github.georgi4511.victbot.command._template;

import com.github.georgi4511.victbot.model.VictCommand;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@Data
@RequiredArgsConstructor
// @Component // remove comment to register command
public class VictCommandTemplate implements VictCommand {
  private final SlashCommandData data = Commands.slash("name", "description");

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    try {

      event.reply("reply").queue();
    } catch (Exception e) {
      if (!event.isAcknowledged()) event.reply("Failed to execute command").queue();
    }
  }
}
