package com.github.georgi4511.victbot.command.misc;

import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.CatPicService;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CatPicCommand extends VictCommand {
  private static final Logger log = LoggerFactory.getLogger(CatPicCommand.class);
  private final SlashCommandData data;
  private final String name;
  private final String description;
  private final CatPicService catPicService;

  public CatPicCommand(CatPicService catPicService) {
    this.name = "cat";
    this.description = "receive random cat";
    this.data = Commands.slash(this.name, this.description);
    this.catPicService = catPicService;
  }

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    event.deferReply().queue();
    String catPicture = catPicService.getRandomCatPicture();
    event.getHook().sendMessage(catPicture).queue();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    CatPicCommand that = (CatPicCommand) o;
    return Objects.equals(data, that.data)
        && Objects.equals(name, that.name)
        && Objects.equals(description, that.description)
        && Objects.equals(catPicService, that.catPicService);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), data, name, description, catPicService);
  }
}
