package com.github.georgi4511.discord_bot.commands;

import com.github.georgi4511.discord_bot.models.SlashCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Ping extends SlashCommand {
    public SlashCommandData data;
    private String name;
    private String description;

    public Ping(){
        this.name = "ping";
        this.description = "sends pong";
        this.data = Commands.slash(this.name,this.description);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        event.reply("Pong!").queue();
    }
}

