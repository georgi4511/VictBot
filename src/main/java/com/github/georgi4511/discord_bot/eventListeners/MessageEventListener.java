package com.github.georgi4511.discord_bot.eventListeners;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageEventListener extends ListenerAdapter {

    Logger logger = LoggerFactory.getLogger(MessageEventListener.class);

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        String content = event.getMessage().getContentRaw();

        if(content.startsWith("!")) {
            logger.info("{}:{}", event.getAuthor().getName(), content);
        }
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!ping")) {
            event.getMessage().reply("Pong!").complete();
        }

    }
    
}