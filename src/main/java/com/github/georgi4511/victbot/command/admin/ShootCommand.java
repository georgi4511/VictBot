package com.github.georgi4511.victbot.command.admin;

import com.github.georgi4511.victbot.model.AbstractVictCommand;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@RequiredArgsConstructor
public class ShootCommand extends AbstractVictCommand {
  public static final String USER = "user";
  public static final String TIME = "time";
  private static final Logger log = LoggerFactory.getLogger(ShootCommand.class);

  private final SlashCommandData data =
      Commands.slash("shoot", "Times out a user")
          .addOption(OptionType.USER, USER, "The user to shoot", true)
          .addOption(OptionType.INTEGER, TIME, "Time out time in seconds", true);

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    User user = Objects.requireNonNull(event.getOption(USER)).getAsUser();

    if (user.isBot()) {
      event.reply("I cannot time out a bot.").queue();
      return;
    }
    int time = Objects.requireNonNull(event.getOption(TIME)).getAsInt();

    Member guildMember =
        Objects.requireNonNull(event.getGuild())
            .findMembers(e -> e.getUser().equals(user))
            .get()
            .getFirst();

    guildMember.timeoutFor(time, TimeUnit.SECONDS).queue();
    event
        .reply(
            String.format(
                "%s you just got shot for %s seconds have fun being timed out",
                guildMember.getNickname(), time))
        .queue();
  }
}
