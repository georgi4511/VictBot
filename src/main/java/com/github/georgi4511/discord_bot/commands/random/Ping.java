package com.github.georgi4511.discord_bot.commands.random;

import com.github.georgi4511.discord_bot.commands.ClientInteraction;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@Getter
@Setter
public class Ping extends ClientInteraction {

    public SlashCommandData data;


    public Ping(){
        this.data = Commands.slash("ping","sends pong").setGuildOnly(true);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        event.reply("Pong!").queue();
    }
}

