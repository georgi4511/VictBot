package com.github.georgi4511.victbot.commands.admin;

import com.github.georgi4511.victbot.models.BaseCommandImpl;
import com.github.georgi4511.victbot.services.DatabaseCacheService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BadBot extends BaseCommandImpl {
    private static final Logger log = LoggerFactory.getLogger(BadBot.class);
    DatabaseCacheService databaseCacheService;
    private SlashCommandData data;
    private String name;
    private String description;


    public BadBot(@NonNull DatabaseCacheService databaseCacheService) {
        this.name = "badbot";
        this.description = "When bot is bad";
        this.data = Commands.slash(this.name, this.description);
        this.databaseCacheService = databaseCacheService;
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
            Integer badBotBotImpressions = databaseCacheService.getBadBotImpressions();
            badBotBotImpressions++;
            databaseCacheService.setBadBotImpressions(badBotBotImpressions);
            event.reply(String.format("I have received %d bad bot impressions. Thank you very much.", badBotBotImpressions)).queue();

        } catch (Exception e) {
            log.error(e.getMessage());
            event.reply("Command failed to execute").queue();
        }
    }

}


