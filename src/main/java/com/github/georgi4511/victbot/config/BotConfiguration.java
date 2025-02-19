/* (C)2025 */
package com.github.georgi4511.victbot.config;

import com.github.georgi4511.victbot.entity.VictCommand;
import com.github.georgi4511.victbot.listener.DiscordEventListener;
import java.util.EnumSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.MissingRequiredPropertiesException;

@Configuration
@RequiredArgsConstructor
@Profile("!dev")
public class BotConfiguration {

  private static final Logger log = LoggerFactory.getLogger(BotConfiguration.class);
  private static final EnumSet<GatewayIntent> intents =
      EnumSet.of(
          GatewayIntent.GUILD_MESSAGES,
          GatewayIntent.GUILD_VOICE_STATES,
          GatewayIntent.MESSAGE_CONTENT,
          GatewayIntent.GUILD_EXPRESSIONS,
          GatewayIntent.SCHEDULED_EVENTS,
          GatewayIntent.GUILD_PRESENCES);

  @Value("${discord.token}")
  private String token;

  @Value("${discord.guild}")
  private String guild;

  @Bean
  public JDA jda(DiscordEventListener discordEventListener, List<VictCommand> commands)
      throws InterruptedException {

    if (token == null) {
      throw new MissingRequiredPropertiesException();
    }

    JDA jda =
        JDABuilder.create(token, intents)
            .setActivity(Activity.listening("Chilling...killing"))
            .addEventListeners(discordEventListener)
            .setStatus(OnlineStatus.DO_NOT_DISTURB)
            .enableCache(CacheFlag.VOICE_STATE)
            .build();

    jda.awaitReady();

    List<SlashCommandData> commandData = commands.stream().map(VictCommand::getData).toList();

    log.info("Commands: {}", commandData.stream().map(SlashCommandData::getName).toList());

    if (guild == null || jda.getGuildById(guild) == null) {
      jda.updateCommands().addCommands(commandData).queue();
    } else {
      Guild guildById = jda.getGuildById(guild);
      assert guildById != null;
      guildById.updateCommands().addCommands(commandData).queue();
    }

    log.info("{} commands set", commands.size());

    return jda;
  }
}
