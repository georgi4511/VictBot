package com.github.georgi4511.victbot.command.misc;

import com.github.georgi4511.victbot.model.AbstractVictCommand;
import com.github.georgi4511.victbot.service.CatFactService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@RequiredArgsConstructor
public class CatFactCommand extends AbstractVictCommand {
  private final CatFactService catFactService;
  private final SlashCommandData data = Commands.slash("cat-fact", "receive random cat fact 🐈");

  @Override
  public void callback(@NotNull SlashCommandInteractionEvent event) {
    String fact = catFactService.getRandomCatFact();
    event.reply(fact).queue();
  }
}
