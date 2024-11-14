package com.github.georgi4511.discord_bot.commands.template;

import com.github.georgi4511.discord_bot.models.VictBaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;


@EqualsAndHashCode(callSuper = true)
@Data
public class CommandTemplate extends VictBaseCommand {
    private SlashCommandData data;
    private String name;
    private String description;

    public CommandTemplate(){
        this.name = "<CHANGE ME>";
        this.description = "<CHANGE ME>";
        this.data = Commands.slash(this.name,this.description);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        event.reply("<CHANGE ME>").queue();
    }
}
