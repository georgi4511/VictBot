package com.github.georgi4511.discord_bot.eventListeners;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event)
    {
        log.info("{} logged in.", event.getJDA().getSelfUser().getName());
        log.info("Commands set");
    }
}