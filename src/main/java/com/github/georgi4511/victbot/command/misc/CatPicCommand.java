package com.github.georgi4511.victbot.command.misc;

import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.CatPicService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class CatPicCommand implements VictCommand {
  private final CatPicService catPicService;
  private final SlashCommandData data = Commands.slash("cat", "receive random cat");

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    event.deferReply().queue();
    String catPicture = catPicService.getRandomCatPicture();
    event.getHook().sendMessage(catPicture).queue();
  }
}
