package com.github.georgi4511.victbot.command.admin;

import com.github.georgi4511.victbot.entity.BaseCommandImpl;
import com.github.georgi4511.victbot.entity.Impressions;
import com.github.georgi4511.victbot.service.ImpressionsService;
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
public class BadBot extends BaseCommandImpl {
    private static final Logger log = LoggerFactory.getLogger(BadBot.class);
    @NonNull
    private final ImpressionsService impressionsService;
    private SlashCommandData data;
    private String name;
    private String description;

    public BadBot(@NotNull ImpressionsService impressionsService) {
        this.name = "badbot";
        this.description = "When bot is bad";
        this.data = Commands.slash(this.name, this.description);
        this.impressionsService = impressionsService;
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
            Guild guild = event.getGuild();
            if (isNull(guild)) {
                throw new UnsupportedOperationException();
            }

            Impressions impressions = impressionsService.getImpressionsByGuildId(guild.getId()).orElse(new Impressions(guild.getId()));
            Integer badBodCount = impressions.getBadBotCount();
            badBodCount++;
            impressions.setBadBotCount(badBodCount);
            impressionsService.saveImpressions(impressions);
            event.reply(String.format("I have received %d bad bot impressions. Thank you very much.", badBodCount)).queue();

        } catch (Exception e) {
            log.error(e.getMessage());
            event.reply("Command failed to execute").setEphemeral(true).queue();
        }
    }


}


