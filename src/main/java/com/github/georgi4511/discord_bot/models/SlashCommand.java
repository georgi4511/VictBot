package com.github.georgi4511.discord_bot.models;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@Setter
@Getter
public abstract class SlashCommand {
    private SlashCommandData data;
    private String name;
    public abstract void callback(SlashCommandInteractionEvent event);
    private Integer cooldown;
    private Permission[] permissions;
    private Boolean devCommand;

}
