package com.github.georgi4511.discord_bot.eventListeners;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReadyListener extends ListenerAdapter {

    Logger logger =LoggerFactory.getLogger(ReadyListener.class);
    @Override
    public void onReady(@NotNull ReadyEvent event)
    {
        logger.info("{} logged in.", event.getJDA().getSelfUser().getName());
    }

}