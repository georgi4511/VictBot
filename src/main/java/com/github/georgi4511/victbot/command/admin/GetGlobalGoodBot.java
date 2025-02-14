package com.github.georgi4511.victbot.command.admin;

import com.github.georgi4511.victbot.entity.BaseCommandImpl;
import com.github.georgi4511.victbot.entity.Impressions;
import com.github.georgi4511.victbot.service.ImpressionsService;
import com.github.georgi4511.victbot.service.VictGuildService;
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
public class GetGlobalGoodBot extends BaseCommandImpl {
    private static final Logger log = LoggerFactory.getLogger(GetGlobalGoodBot.class);
    @NonNull
    private final ImpressionsService impressionsService;
    private final VictGuildService victGuildService;
    private SlashCommandData data;
    private String name;
    private String description;

    public GetGlobalGoodBot(@NotNull ImpressionsService impressionsService, VictGuildService victGuildService) {
        this.name = "get-global-goodbot";
        this.description = "Get amount of bot is good sent globally";
        this.data = Commands.slash(this.name, this.description);
        this.impressionsService = impressionsService;
        this.victGuildService = victGuildService;

    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
            Integer sum = impressionsService.getAllImpressions().stream().map(Impressions::getGoodBotCount).reduce(0, Integer::sum);

            event.reply(String.format("I have received globally %d good bot impressions. Thank you globally.", sum)).queue();
        } catch (Exception e) {
            log.error(e.getMessage());
            event.getHook().sendMessage("Command failed to execute").setEphemeral(true).queue();
        }
    }


}


