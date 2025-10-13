package com.github.georgi4511.victbot.command.misc;

import com.github.georgi4511.victbot.command.AbstractVictCommand;
import com.github.georgi4511.victbot.service.CatPicService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@RequiredArgsConstructor
public class CatPicCommand extends AbstractVictCommand {
  private final CatPicService catPicService;
  private final SlashCommandData data = Commands.slash("cat", "receive random cat");

  @Override
  protected void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent) {
    String catPicture = catPicService.getRandomCatPicture();
    slashCommandInteractionEvent.reply(catPicture).queue();
  }
}
