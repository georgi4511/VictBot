package com.github.georgi4511.victbot.command.utility;

import com.github.georgi4511.victbot.model.VictCommand;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class GetTimeCommand extends VictCommand {
  private SlashCommandData data;
  private String name;
  private String description;
  private Boolean devCommand = true;

  public GetTimeCommand() {
    this.name = "get-time";
    this.description = "gets the time, cmon";
    this.data = Commands.slash(this.name, this.description);
  }

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    event
        .reply(
            Instant.now()
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
        .queue();
  }
}
