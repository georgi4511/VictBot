package com.github.georgi4511.victbot.command.bookmark;

import com.github.georgi4511.victbot.model.AbstractVictCommand;
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
public class RemoveBookmarkCommand extends AbstractVictCommand {
  public static final String ALIAS = "alias";
  private final SlashCommandData data =
      Commands.slash("remove-bookmark", "Removes a bookmark by user using alias")
          .addOption(OptionType.STRING, ALIAS, "Alias of the bookmark", true);
  private final BookmarkService bookmarkService;

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    String alias = Objects.requireNonNull(event.getOption(ALIAS)).getAsString();
    String userDiscordId = Objects.requireNonNull(event.getUser()).getId();

    boolean result = bookmarkService.removeByAliasAndVictUserId(alias, userDiscordId);

    if (!result) {
      event.reply("Failed to remove bookmark").queue();
      return;
    }
    event.reply("Bookmark removed").queue();
  }
}
