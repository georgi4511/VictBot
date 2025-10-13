package com.github.georgi4511.victbot.command.bookmark;

import com.github.georgi4511.victbot.command.AbstractVictCommand;
import com.github.georgi4511.victbot.service.BookmarkService;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@RequiredArgsConstructor
public class AddBookmarkCommand extends AbstractVictCommand {
  public static final String ALIAS = "alias";
  public static final String RESPONSE = "response";
  private final SlashCommandData data =
      Commands.slash("create-bookmark", "Creates a bookmark")
          .addOption(OptionType.STRING, ALIAS, "Alias for the bookmark", true)
          .addOption(OptionType.STRING, RESPONSE, "Response for the bookmark", true);
  private final BookmarkService bookmarkService;

  @Override
  protected void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent) {
    String alias = Objects.requireNonNull(slashCommandInteractionEvent.getOption(ALIAS)).getAsString();
    String response = Objects.requireNonNull(slashCommandInteractionEvent.getOption(RESPONSE)).getAsString();

    String guildId = null;
    if (slashCommandInteractionEvent.isFromGuild()) {
      guildId = Objects.requireNonNull(slashCommandInteractionEvent.getGuild()).getId();
    }
    String userId = Objects.requireNonNull(slashCommandInteractionEvent.getUser()).getId();

    bookmarkService.create(alias, response, guildId, userId);

    slashCommandInteractionEvent.reply("Bookmark created with alias: %s".formatted(alias)).queue();
  }
}
