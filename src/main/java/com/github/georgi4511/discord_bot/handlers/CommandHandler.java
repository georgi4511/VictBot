package com.github.georgi4511.discord_bot.handlers;

import com.github.georgi4511.discord_bot.models.SlashCommand;
import com.github.georgi4511.discord_bot.models.SlashCommandStringSelectMenu;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CommandHandler {
    private final List<SlashCommand> commands;
    private final List<SlashCommandStringSelectMenu> stringSelectMenuCommands;
    private final JDA jda;

    public void registerCommands() {

        List<SlashCommandData> finalList = new ArrayList<>();
        finalList.addAll(commands.stream()
                .map(SlashCommand::getData).toList());
//        finalList.addAll(
//                stringSelectMenuCommands.stream().map(SlashCommandStringSelectMenu::getData).toList());

        this.jda.updateCommands()
                .addCommands(finalList)
                .queue();
        log.info("Commands set");
    }

    public SlashCommand getCommandByName(String name) {
        return commands.stream()
                .filter(command -> command.getName().equals(name))
                .findFirst()
                .orElse( null);
//                        stringSelectMenuCommands.stream().filter(c->c.getName().equals(name)).findFirst().orElse(null));
    }
    public SlashCommandStringSelectMenu getSlashCommandStringSelectMenuByCustomId(String id){
        return stringSelectMenuCommands.stream().filter(command->command.getCustomId().equals(id)).findFirst().orElse(null);
    }
}
