package com.github.georgi4511.victbot.config;

import com.github.georgi4511.victbot.listener.DiscordEventListener;
import java.util.EnumSet;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.MissingRequiredPropertiesException;

@Configuration
@RequiredArgsConstructor
@Profile("!dev")
public class BotConfig {

  private static final EnumSet<GatewayIntent> intents =
      EnumSet.of(
          GatewayIntent.GUILD_MESSAGES,
          GatewayIntent.GUILD_VOICE_STATES,
          GatewayIntent.MESSAGE_CONTENT,
          GatewayIntent.GUILD_EXPRESSIONS,
          GatewayIntent.SCHEDULED_EVENTS,
          GatewayIntent.GUILD_PRESENCES);

  @Value("${vict.discord.token}")
  private String token;

  @Bean
  JDA jda(DiscordEventListener discordEventListener) throws InterruptedException {

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

    return jda;
  }
}
