package com.github.georgi4511.victbot.command.misc;

import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.CatFactService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class CatFactCommand implements VictCommand {
  private static final Logger log = LoggerFactory.getLogger(CatFactCommand.class);
  private final CatFactService catFactService;
  private final SlashCommandData data = Commands.slash("cat-fact", "receive random cat fact 🐈");

  @Override
  public void callback(@NotNull SlashCommandInteractionEvent event) {
    event.deferReply().queue();
    String fact = catFactService.getRandomCatFact();
    event.getHook().sendMessage(fact).queue();
  }
}
