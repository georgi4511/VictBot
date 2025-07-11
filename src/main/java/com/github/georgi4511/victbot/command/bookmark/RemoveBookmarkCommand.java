package com.github.georgi4511.victbot.command.bookmark;

import com.github.georgi4511.victbot.command.AbstractVictCommand;
import com.github.georgi4511.victbot.model.Bookmark;
import com.github.georgi4511.victbot.service.BookmarkService;
import java.util.Objects;
import java.util.Optional;
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
public class RemoveBookmarkCommand extends AbstractVictCommand {
  public static final String ALIAS = "alias";
  private final SlashCommandData data =
      Commands.slash("remove-bookmark", "Removes a bookmark by user using alias")
          .addOption(OptionType.STRING, ALIAS, "Alias of the bookmark", true);
  private final BookmarkService bookmarkService;

  @Override
  protected void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent) {
    String alias = Objects.requireNonNull(slashCommandInteractionEvent.getOption(ALIAS)).getAsString();
    String victUserId = Objects.requireNonNull(slashCommandInteractionEvent.getUser()).getId();

    Optional<Bookmark> optionalBookmark =
        bookmarkService.getByAliasAndVictUserId(alias, victUserId);

    if (optionalBookmark.isEmpty()) {
      slashCommandInteractionEvent
          .reply(
              String.format(
                  "Bookmark does not exist for user %s", slashCommandInteractionEvent.getUser().getEffectiveName()))
          .queue();
      return;
    }

    boolean result = bookmarkService.removeByAliasAndVictUserId(alias, victUserId);

    slashCommandInteractionEvent.reply(result ? "Bookmark removed" : "Failed to remove bookmark").queue();
  }
}
