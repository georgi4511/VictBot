package com.github.georgi4511.victbot.command.admin;

import com.github.georgi4511.victbot.entity.BaseCommandImpl;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.service.ImpressionsService;
import com.github.georgi4511.victbot.service.VictGuildService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Getter
@Setter
@Component
public class GoodBot extends BaseCommandImpl {
    private static final Logger log = LoggerFactory.getLogger(GoodBot.class);
    @NonNull
    private final ImpressionsService impressionsService;
    private final VictGuildService victGuildService;
    private SlashCommandData data;
    private String name;
    private String description;

    public GoodBot(@NotNull ImpressionsService impressionsService, VictGuildService victGuildService) {
        this.name = "goodbot";
        this.description = "When bot is good";
        this.data = Commands.slash(this.name, this.description);
        this.impressionsService = impressionsService;
        this.victGuildService = victGuildService;
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {

        try {
            Guild guild = event.getGuild();
            if (isNull(guild)) {
                throw new UnsupportedOperationException();
            }

            VictGuild victGuild = victGuildService.findVictGuildByDiscordIdOrCreate(guild.getId());

            Integer goodBotCount = victGuildService.incrementGoodBotCount(victGuild);

            event.reply(String.format("I have received %d good bot impressions. Thank you very much.", goodBotCount)).queue();

        } catch (Exception e) {
            log.error(e.getMessage());
            event.getHook().sendMessage("Command failed to execute").setEphemeral(true).queue();
        }
    }
}