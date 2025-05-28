package com.github.georgi4511.victbot.command._template;

import com.github.georgi4511.victbot.model.AbstractVictCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
// @Component // remove comment to register command
class VictCommandTemplate extends AbstractVictCommand {
  private static final Logger log = LoggerFactory.getLogger(VictCommandTemplate.class);
  private final SlashCommandData data = Commands.slash("name", "description");

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    event.reply("reply").queue();
  }
}
