package com.github.georgi4511.discord_bot.eventListeners;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class MessageEventListener extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        String content = event.getMessage().getContentRaw();

        if(content.startsWith("!")) {
            log.info("{}:{}", event.getAuthor().getName(), content);
        }
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!ping")) {
            event.getMessage().reply("Pong!").complete();
        }

    }
    
}