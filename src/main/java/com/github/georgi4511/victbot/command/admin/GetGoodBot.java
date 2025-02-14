package com.github.georgi4511.victbot.command.admin;

import com.github.georgi4511.victbot.entity.BaseCommandImpl;
import com.github.georgi4511.victbot.entity.Impressions;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.service.ImpressionsService;
import com.github.georgi4511.victbot.service.VictGuildService;
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

import static java.util.Objects.isNull;

@Getter
@Setter
@Component
public class GetGoodBot extends BaseCommandImpl {
    private static final Logger log = LoggerFactory.getLogger(GetGoodBot.class);
    @NonNull
    private final ImpressionsService impressionsService;
    private final VictGuildService victGuildService;
    private SlashCommandData data;
    private String name;
    private String description;

    public GetGoodBot(@NotNull ImpressionsService impressionsService, VictGuildService victGuildService) {
        this.name = "get-goodbot";
        this.description = "Get amount of bot is good sent";
        this.data = Commands.slash(this.name, this.description).addOption(OptionType.BOOLEAN, "global", "guild only or global?");
        this.impressionsService = impressionsService;
        this.victGuildService = victGuildService;

    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
            boolean doGlobal = event.getOptionsByName("global").getFirst().getAsBoolean();

            Integer sum;
            if (doGlobal) {
                sum = impressionsService.getAllImpressions().stream().map(Impressions::getGoodBotCount).reduce(0, Integer::sum);
            } else {
                Guild guild = event.getGuild();
                if (isNull(guild)) {
                    throw new UnsupportedOperationException("Global but not in guild");
                }
                VictGuild victGuild = victGuildService.findVictGuildByDiscordIdOrCreate(guild.getId());
                Impressions impressions = victGuild.getImpressions();
                if (isNull(impressions)) {
                    impressions = new Impressions(victGuild);
                    impressionsService.saveImpressions(impressions);
                }
                sum = impressions.getGoodBotCount();
            }
            event.reply(String.format("I have received globally %d good bot impressions. Thank you globally.", sum)).queue();
        } catch (Exception e) {
            log.error(e.getMessage());
            event.getHook().sendMessage("Command failed to execute").setEphemeral(true).queue();
        }
    }


}


