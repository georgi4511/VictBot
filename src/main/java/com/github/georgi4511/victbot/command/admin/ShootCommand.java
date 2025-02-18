/* (C)2025 */
package com.github.georgi4511.victbot.command.admin;

import com.github.georgi4511.victbot.entity.VictCommand;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ShootCommand extends VictCommand {
  public static final String USER = "user";
  public static final String TIME = "time";
  private static final Logger log = LoggerFactory.getLogger(ShootCommand.class);
  private SlashCommandData data;
  private String name;
  private String description;

  public ShootCommand() {
    this.name = "shoot";
    this.description = "Times out a user";
    this.data =
        Commands.slash(this.name, this.description)
            .addOption(OptionType.USER, USER, "The user to shoot", true)
            .addOption(OptionType.INTEGER, TIME, "Time out time in seconds", true);
  }

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    try {
      User user = Objects.requireNonNull(event.getOption(USER)).getAsUser();

      if (user.isBot()) {
        event.reply("I cannot time out a bot.").queue();
        return;
      }
      int time = Objects.requireNonNull(event.getOption(TIME)).getAsInt();

      Member guildMember =
          Objects.requireNonNull(event.getGuild())
              .findMembers(e -> e.equals(user))
              .get()
              .getFirst();

      guildMember.timeoutFor(time, TimeUnit.SECONDS).queue();
      event
          .reply(
              String.format(
                  "%s you just got shot for %s seconds have fun being timed out",
                  guildMember.getNickname(), time))
          .queue();

    } catch (Exception e) {
      log.error(e.getMessage());
      event.reply("Command failed to execute").queue();
    }
  }
}
