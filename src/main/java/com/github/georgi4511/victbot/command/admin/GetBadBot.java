package com.github.georgi4511.victbot.command.admin;

import com.github.georgi4511.victbot.entity.BaseCommandImpl;
import com.github.georgi4511.victbot.entity.Impressions;
import com.github.georgi4511.victbot.service.ImpressionsService;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.isNull;

@Getter
@Setter
@Component
public class GetBadBot extends BaseCommandImpl {
    private static final Logger log = LoggerFactory.getLogger(GetBadBot.class);
    @NonNull
    private final ImpressionsService impressionsService;
    private SlashCommandData data;
    private String name;
    private String description;

    public GetBadBot(@NotNull ImpressionsService impressionsService) {
        this.name = "get-badbot";
        this.description = "Get amount of bot is bad sent";
        this.data = Commands.slash(this.name, this.description).addOption(OptionType.BOOLEAN, "global", "guild only or global?");
        this.impressionsService = impressionsService;
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
            boolean doGlobal = event.getOptionsByName("global").getFirst().getAsBoolean();

            Integer sum;
            if (doGlobal) {
                sum = impressionsService.getAllImpressions().stream().map(Impressions::getBadBotCount).reduce(0, Integer::sum);
            } else {
                Guild guild = event.getGuild();
                if (isNull(guild)) throw new UnsupportedOperationException("Global but not in guild");
                Optional<Impressions> impressions = impressionsService.getImpressionsByGuildId(guild.getId());
                sum = impressions.map(Impressions::getBadBotCount).orElseGet(() -> {
                    Impressions newImpressions = new Impressions(guild.getId());
                    impressionsService.saveImpressions(newImpressions);
                    return newImpressions.getBadBotCount();
                });
            }
            event.reply(String.format("I have received globally %d bad bot impressions. Thank you very much.", sum)).queue();
        } catch (Exception e) {
            log.error(e.getMessage());
            event.reply("Command failed to execute").setEphemeral(true).queue();
        }
    }


}


