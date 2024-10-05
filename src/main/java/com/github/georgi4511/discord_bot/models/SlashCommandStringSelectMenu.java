package com.github.georgi4511.discord_bot.models;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@Setter
@Getter
public abstract class SlashCommandStringSelectMenu extends SlashCommand {
    private SlashCommandData data;
    private String name;
    private String customId;
    public abstract void callback(SlashCommandInteractionEvent event);
    public abstract void selectMenuCallback(StringSelectInteractionEvent event);
    private Integer cooldown;
    private Permission[] permissions;
    private Boolean devCommand;

}
