package com.github.georgi4511.discord_bot.bot;
import com.github.georgi4511.discord_bot.commands.InteractionList;
import com.github.georgi4511.discord_bot.commands.random.Ping;
import com.github.georgi4511.discord_bot.eventListeners.InteractionEventListener;
import com.github.georgi4511.discord_bot.eventListeners.MessageEventListener;
import com.github.georgi4511.discord_bot.eventListeners.ReadyListener;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

import static com.github.georgi4511.discord_bot.jsonService.JsonService.instantiateJsons;
import static java.util.Objects.isNull;

@Service
public class Bot {
    public static Logger logger= LoggerFactory.getLogger(Bot.class);


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
                GatewayIntent.SCHEDULED_EVENTS
        );

        JDA client = JDABuilder.createDefault(TOKEN, intents)
                .addEventListeners(new ReadyListener(), new MessageEventListener(), new InteractionEventListener())
                .setActivity(Activity.listening("Chilling...killing"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .build();

        client.updateCommands().addCommands(Ping.data).queue();
    }
}
