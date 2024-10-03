package com.github.georgi4511.discord_bot.handlers;

import com.github.georgi4511.discord_bot.models.SlashCommand;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class CommandHandler {
    private final List<SlashCommand> commands;
    private final JDA jda;

    @PostConstruct
    public void registerCommands() {
        jda.updateCommands().addCommands(
                commands.stream()
                        .map(command -> Commands.slash(command.getData().getName(), command.getData().getDescription()))
                        .toArray(CommandData[]::new)
        ).queue();
    }

    public SlashCommand getCommandByName(String name) {
        return commands.stream()
                .filter(command -> command.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
