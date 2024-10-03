package com.github.georgi4511.discord_bot.eventListeners;
import com.github.georgi4511.discord_bot.commands.ClientInteraction;
import com.github.georgi4511.discord_bot.commands.random.CatFact;
import com.github.georgi4511.discord_bot.commands.random.CatPic;
import com.github.georgi4511.discord_bot.commands.random.Ping;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;


@Slf4j
public class InteractionEventListener extends ListenerAdapter {

    static public HashSet<ClientInteraction> COMMAND_LIST = new HashSet<>();


    public InteractionEventListener(){
        COMMAND_LIST.addAll(List.of(new Ping(),new CatFact(),new CatPic()));
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
    {
       COMMAND_LIST
                .forEach(interaction->
                {
                    System.out.println(event.getName()+" "+interaction.getData().getName());
                    if(event.getName().equalsIgnoreCase(interaction.getData().getName())) {
                        interaction.callback(event);
                    }
    });
    }
}