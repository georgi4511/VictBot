package com.github.georgi4511.discord_bot.bot;
import com.github.georgi4511.discord_bot.commands.random.CatFact;
import com.github.georgi4511.discord_bot.commands.random.CatPic;
import com.github.georgi4511.discord_bot.commands.random.Ping;
import com.github.georgi4511.discord_bot.eventListeners.InteractionEventListener;
import com.github.georgi4511.discord_bot.eventListeners.MessageEventListener;
import com.github.georgi4511.discord_bot.eventListeners.ReadyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.Set;

import static com.github.georgi4511.discord_bot.jsonService.JsonService.instantiateJsons;

public class Bot {
    public static Logger logger= LoggerFactory.getLogger(Bot.class);

    public static void createBot(String[] args) throws Exception {

        if(!(args.length>0)){
            throw new Exception("TOKEN key required in program arguments");
        }

        instantiateJsons();

        final String token = args[0];
        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.SCHEDULED_EVENTS,
                GatewayIntent.GUILD_PRESENCES
        );

        JDA client = JDABuilder.create(token, intents)
                .addEventListeners(new ReadyListener(), new MessageEventListener(), new InteractionEventListener())
                .setActivity(Activity.listening("Chilling...killing"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .enableCache(CacheFlag.VOICE_STATE)
                .build();

        client.updateCommands().addCommands(new Ping().getData(),new CatPic().getData(),new CatFact().getData()).queue();
    }
}
