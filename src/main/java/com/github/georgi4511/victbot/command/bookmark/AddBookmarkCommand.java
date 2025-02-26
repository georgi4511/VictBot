package com.github.georgi4511.victbot.command.bookmark;

import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.BookmarkService;
import java.util.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class AddBookmarkCommand implements VictCommand {
  public static final String ALIAS = "alias";
  public static final String RESPONSE = "response";
  private final SlashCommandData data =
      Commands.slash("create-bookmark", "Creates a bookmark")
          .addOption(OptionType.STRING, ALIAS, "Alias for the bookmark", true)
          .addOption(OptionType.STRING, RESPONSE, "Response for the bookmark", true);
  private final BookmarkService bookmarkService;

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    try {

      String alias = Objects.requireNonNull(event.getOption(ALIAS)).getAsString();
      String response = Objects.requireNonNull(event.getOption(RESPONSE)).getAsString();

      String guildId = null;
      if (event.isFromGuild()) {
        guildId = Objects.requireNonNull(event.getGuild()).getId();
      }
      String userId = Objects.requireNonNull(event.getUser()).getId();

      bookmarkService.addBookmark(alias, response, guildId, userId);

      event.reply("Bookmark created with alias: %s".formatted(alias)).queue();
    } catch (Exception e) {
      if (!event.isAcknowledged()) event.reply("Failed to execute command").queue();
    }
  }
}
