package com.github.georgi4511.discord_bot.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public abstract class ClientInteraction {
    public SlashCommandData data;
    public abstract void callback(SlashCommandInteractionEvent event);
    public Integer cooldown;
    public Permission[] permissions;
    public Boolean devCommand;

}
