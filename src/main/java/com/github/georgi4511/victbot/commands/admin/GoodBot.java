package com.github.georgi4511.victbot.commands.admin;

import com.github.georgi4511.victbot.models.BaseCommandImpl;
import com.github.georgi4511.victbot.services.DatabaseCacheService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class GoodBot extends BaseCommandImpl {
    private static final Logger log = LoggerFactory.getLogger(GoodBot.class);
    @NonNull
    private final DatabaseCacheService databaseCacheService;
    private SlashCommandData data;
    private String name;
    private String description;

    public GoodBot(@NotNull DatabaseCacheService databaseCacheService) {
        this.name = "goodbot";
        this.description = "When bot is good";
        this.data = Commands.slash(this.name, this.description);
        this.databaseCacheService = databaseCacheService;
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
            Integer goodBotBotImpressions = databaseCacheService.getGoodBotImpressions();
            goodBotBotImpressions++;
            databaseCacheService.setGoodBotImpressions(goodBotBotImpressions);
            event.reply(String.format("I have received %d good bot impressions. Thank you very much.", goodBotBotImpressions)).queue();

        } catch (Exception e) {
            log.error(e.getMessage());
            event.reply("Command failed to execute").queue();
        }
    }

}


