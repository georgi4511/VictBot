package com.github.georgi4511.discord_bot.handlers;

import com.github.georgi4511.discord_bot.models.SlashCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CommandHandler {
    private final List<SlashCommand> commands;
    private final JDA jda;

    public void registerCommands() {

        List<SlashCommandData> commandArray = commands.stream()
                .map(SlashCommand::getData)
                .toList();

        this.jda.updateCommands()
                .addCommands(commandArray)
                .queue();
        log.info("Commands set");
    }

    public SlashCommand getCommandByName(String name) {
        return commands.stream()
                .filter(command -> command.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
