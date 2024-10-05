package com.github.georgi4511.discord_bot.config;

import com.github.georgi4511.discord_bot.listeners.DiscordEventListener;
import com.github.georgi4511.discord_bot.models.VictBaseCommand;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;
import java.util.List;
@Slf4j
@Configuration
public class BotConfiguration {

    @Value("${discord.token}")
    private String token;

    @Bean
    public JDA jda(DiscordEventListener discordEventListener, List<VictBaseCommand> commands) {

        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.SCHEDULED_EVENTS,
                GatewayIntent.GUILD_PRESENCES
        );

        JDA jda = JDABuilder.create(token,intents)
                .setActivity(Activity.listening("Chilling...killing"))
                .addEventListeners(discordEventListener)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .enableCache(CacheFlag.VOICE_STATE)
                .build();

        List<SlashCommandData> commandData = commands.stream().map(VictBaseCommand::getData).toList();
        jda.updateCommands().addCommands(commandData).queue();

        log.info(String.format("%d commands set",commands.size()));

        return jda;
    }
}
