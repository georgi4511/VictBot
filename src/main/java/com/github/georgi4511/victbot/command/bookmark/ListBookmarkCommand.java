package com.github.georgi4511.victbot.command.bookmark;

import com.github.georgi4511.victbot.command.AbstractVictCommand;
import com.github.georgi4511.victbot.model.Bookmark;
import com.github.georgi4511.victbot.service.BookmarkService;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@RequiredArgsConstructor
public class ListBookmarkCommand extends AbstractVictCommand {
  public static final String SHOW_GUILDS = "show-guilds";
  private final SlashCommandData data =
      Commands.slash("list-bookmark", "Lists bookmarks created by user in server")
          .addOption(
              OptionType.BOOLEAN,
              SHOW_GUILDS,
              "to show the guilds reminders instead of you own",
              false);
  private final BookmarkService bookmarkService;

  @Override
  protected void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent) {
    List<Bookmark> bookmarks = getBookmarks(slashCommandInteractionEvent);

    if (bookmarks.isEmpty()) {
      slashCommandInteractionEvent.reply("No bookmarks found").queue();
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
                                .atZone(ZoneId.of("UTC"))
                                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                            bookmark.getAlias(),
                            bookmark.getResponse()))
            .reduce("", String::concat);

    slashCommandInteractionEvent.reply(message).queue();
  }

  private List<Bookmark> getBookmarks(SlashCommandInteractionEvent event) {

    OptionMapping bookmarksGuildOption = event.getOption(SHOW_GUILDS);

    List<Bookmark> bookmarks;

    if (!event.isFromGuild()) {
      bookmarks = bookmarkService.getByVictUserId(event.getUser().getId());
    } else if (bookmarksGuildOption != null && bookmarksGuildOption.getAsBoolean()) {
      bookmarks =
          bookmarkService.getByVictGuildId(Objects.requireNonNull(event.getGuild()).getId());
    } else {
      bookmarks =
          bookmarkService.getByGuildAndUser(
              Objects.requireNonNull(event.getGuild()).getId(), event.getUser().getId());
    }

    return bookmarks;
  }
}
