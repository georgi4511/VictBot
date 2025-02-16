package com.github.georgi4511.victbot.command.admin;

import com.github.georgi4511.victbot.entity.Impressions;
import com.github.georgi4511.victbot.entity.VictCommand;
import com.github.georgi4511.victbot.service.ImpressionsService;
import com.github.georgi4511.victbot.service.VictGuildService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;

@Getter
@Setter
@Component
public class GetBotImpressions extends VictCommand {
    private static final Logger log = LoggerFactory.getLogger(GetBotImpressions.class);
    @NonNull
    private final ImpressionsService impressionsService;
    private final VictGuildService victGuildService;
    private SlashCommandData data;
    private String name;
    private String description;

    public GetBotImpressions(@NotNull ImpressionsService impressionsService, VictGuildService victGuildService) {
        this.name = "get-bot-impressions";
        this.description = "Get amount of bot is good/bad sent in server/globally";
        this.data = Commands.slash(this.name, this.description).addOption(OptionType.BOOLEAN, "global", "do the search globally or only for this server");
        this.impressionsService = impressionsService;
        this.victGuildService = victGuildService;

    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {

            boolean global = true;
            OptionMapping globalOption = event.getOption("global");
            if (!isNull(globalOption)) {
                global = globalOption.getAsBoolean();
            }

            if (global) {
                returnGlobalImpressions(event);
                return;
            }
            returnGuildImpressions(event);

        } catch (Exception e) {
            log.error(e.getMessage());
            event.getHook().sendMessage("Command failed to execute").setEphemeral(true).queue();
        }
    }

    private void returnGuildImpressions(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if (isNull(guild)) {
            throw new UnsupportedOperationException("Global but not in guild");
        }
        Impressions impressions = impressionsService.getImpressionsOrCreateByDiscordId(guild.getId());

        event.reply(String.format("I have received %d good bots and %d bad bots in this server", impressions.getGoodBotCount(), impressions.getBadBotCount())).queue();
    }

    private void returnGlobalImpressions(SlashCommandInteractionEvent event) {
        List<Impressions> allImpressions = impressionsService.getAllImpressions();

        Integer badBotSum = allImpressions.stream().map(Impressions::getBadBotCount).reduce(0, Integer::sum);
        Integer goodBotSum = allImpressions.stream().map(Impressions::getGoodBotCount).reduce(0, Integer::sum);

        event.reply(String.format("I have received %d good bots and %d bad bots globally", goodBotSum, badBotSum)).queue();
    }


}


