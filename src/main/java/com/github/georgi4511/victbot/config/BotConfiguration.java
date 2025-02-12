package com.github.georgi4511.victbot.config;

import com.github.georgi4511.victbot.listeners.DiscordEventListener;
import com.github.georgi4511.victbot.models.BaseCommandImpl;
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
import org.springframework.core.env.MissingRequiredPropertiesException;

import java.util.EnumSet;
import java.util.List;

import static java.util.Objects.isNull;

@Configuration
public class BotConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BotConfiguration.class);
    private static final EnumSet<GatewayIntent> intents = EnumSet.of(
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_VOICE_STATES,
            GatewayIntent.MESSAGE_CONTENT,
            GatewayIntent.GUILD_EXPRESSIONS,
            GatewayIntent.SCHEDULED_EVENTS,
            GatewayIntent.GUILD_PRESENCES
    );
    @Value("${discord.token}")
    private static String token;
    @Value("${discord.guild}")
    private static String guild;

    private static JDA jda;

    private BotConfiguration() {
        throw new UnsupportedOperationException();
    }

    @Bean
    public static JDA jda(DiscordEventListener discordEventListener, List<BaseCommandImpl> commands) throws InterruptedException {

        if (isNull(token)) {
            throw new MissingRequiredPropertiesException();
        }

        jda = JDABuilder.create(token, intents)
                .setActivity(Activity.listening("Chilling...killing"))
                .addEventListeners(discordEventListener)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .enableCache(CacheFlag.VOICE_STATE)
                .build();

        jda.awaitReady();

        addCommands(commands);
        log.info("{} commands set", commands.size());

        return jda;
    }

    private static void addCommands(List<BaseCommandImpl> commands) {
        List<SlashCommandData> commandData = commands.stream().map(BaseCommandImpl::getData).toList();

        log.info("Commands: {}", commandData);

        if (!isNull(guild)) {
            Guild guildById = jda.getGuildById(guild);
            if (!isNull(guildById)) {
                guildById.updateCommands().addCommands(commandData).queue();
                return;
            }
        }
        jda.updateCommands().addCommands(commandData).queue();
    }
}
