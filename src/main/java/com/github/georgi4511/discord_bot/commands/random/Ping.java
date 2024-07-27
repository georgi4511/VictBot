package com.github.georgi4511.discord_bot.commands.random;

import com.github.georgi4511.discord_bot.commands.ClientInteraction;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class Ping extends ClientInteraction {

    public static SlashCommandData data = Commands.slash("ping","sends pong");

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        event.reply("Pong!").queue();
    }
}

