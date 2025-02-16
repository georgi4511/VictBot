package com.github.georgi4511.victbot.command._template;

import com.github.georgi4511.victbot.entity.VictCommand;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;


@Getter
@Setter
public class VictCommandTemplate extends VictCommand {
    private SlashCommandData data;
    private String name;
    private String description;

    public VictCommandTemplate() {
        this.name = "<CHANGE ME>";
        this.description = "<CHANGE ME>";
        this.data = Commands.slash(this.name, this.description);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        event.reply("<CHANGE ME>").queue();
    }
}

