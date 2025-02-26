package com.github.georgi4511.victbot.command.bookmark;

import com.github.georgi4511.victbot.model.Bookmark;
import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.BookmarkService;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class ListBookmarkCommand implements VictCommand {
  private final SlashCommandData data =
      Commands.slash("list-bookmark", "Lists bookmarks created by user in server");
  private final BookmarkService bookmarkService;

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    try {

      String guildId = null;
      if (event.isFromGuild()) {
        guildId = Objects.requireNonNull(event.getGuild()).getId();
      }
      String userId = Objects.requireNonNull(event.getUser()).getId();

      List<Bookmark> bookmarks = bookmarkService.getBookmarksByGuildAndUser(guildId, userId);

      if (bookmarks.isEmpty()) {
        event.reply("No bookmarks found").queue();
        return;
      }

      String message =
          bookmarks.stream()
              .map(
                  bookmark ->
                      "%s - %s - %s%n"
                          .formatted(
                              bookmark
                                  .getCreatedTime()
                                  .atZone(ZoneId.systemDefault())
                                  .format(
                                      DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                              bookmark.getAlias(),
                              bookmark.getResponse()))
              .reduce(String::concat)
              .orElse("No bookmarks found");

      event.reply(message).queue();

    } catch (Exception e) {
      if (!event.isAcknowledged()) event.reply("Failed to execute command").queue();
    }
  }
}
