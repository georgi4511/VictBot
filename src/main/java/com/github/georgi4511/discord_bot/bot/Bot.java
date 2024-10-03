package com.github.georgi4511.discord_bot.bot;
import com.github.georgi4511.discord_bot.commands.random.CatFact;
import com.github.georgi4511.discord_bot.commands.random.CatPic;
import com.github.georgi4511.discord_bot.commands.random.Ping;
import com.github.georgi4511.discord_bot.eventListeners.InteractionEventListener;
import com.github.georgi4511.discord_bot.eventListeners.MessageEventListener;
import com.github.georgi4511.discord_bot.eventListeners.ReadyListener;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.dv8tion.jda.api.utils.cache.CacheFlag;
import java.util.EnumSet;

import static com.github.georgi4511.discord_bot.jsonService.JsonService.instantiateJsons;
import static java.util.Objects.isNull;

@Slf4j
@Service
public class Bot {


    @Value("${discord.token}")
    private String TOKEN;

    @PostConstruct
    public void startBot() throws Exception {

        if (isNull(TOKEN) || TOKEN.isEmpty())
        {
            throw new Exception("No Token");
        }

        instantiateJsons();

        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.SCHEDULED_EVENTS,
                GatewayIntent.GUILD_PRESENCES
        );

        JDA client = JDABuilder.create(TOKEN, intents)
                .addEventListeners(new ReadyListener(), new MessageEventListener(), new InteractionEventListener())
                .setActivity(Activity.listening("Chilling...killing"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .enableCache(CacheFlag.VOICE_STATE)
                .build();

        client.updateCommands().addCommands(new Ping().getData(),new CatPic().getData(),new CatFact().getData()).queue();
    }
}
