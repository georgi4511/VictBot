package com.github.georgi4511.discord_bot.eventListeners;
import com.github.georgi4511.discord_bot.commands.InteractionList;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InteractionEventListener extends ListenerAdapter {

    Logger logger = LoggerFactory.getLogger(InteractionEventListener.class);

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
    {
        super.onSlashCommandInteraction(event);
        InteractionList.COMMAND_LIST
                .forEach(interaction->
                {if(event.getName().equalsIgnoreCase(interaction.data.getName()))
                interaction.callback(event);
                });
    }
    
}