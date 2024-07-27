package com.github.georgi4511.discord_bot.commands;

import com.github.georgi4511.discord_bot.commands.random.Ping;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.HashSet;

public class InteractionList {

    public static HashSet<ClientInteraction> COMMAND_LIST = new HashSet<>();

    InteractionList(){
    COMMAND_LIST.add(new Ping());
    }


}