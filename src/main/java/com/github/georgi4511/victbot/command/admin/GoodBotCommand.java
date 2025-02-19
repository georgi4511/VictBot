/* (C)2025 */
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
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter
@Component
public class GoodBotCommand extends VictCommand {
    private static final Logger log = LoggerFactory.getLogger(GoodBotCommand.class);
    @NonNull
    private final ImpressionsService impressionsService;
    private final VictGuildService victGuildService;
    private SlashCommandData data;
    private String name;
    private String description;

    public GoodBotCommand(
            @NotNull ImpressionsService impressionsService, VictGuildService victGuildService) {
        this.name = "good-bot";
        this.description = "When bot is good";
        this.data = Commands.slash(this.name, this.description);
        this.impressionsService = impressionsService;
        this.victGuildService = victGuildService;
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
            Guild guild = event.getGuild();
            if (guild == null) {
                throw new UnsupportedOperationException();
            }

            Impressions impressions =
                    impressionsService.incrementImpressionsByDiscordId(guild.getId(), true);

            event
                    .reply(
                            String.format(
                                    "I have received %d good bot impressions. Thank you very much.",
                                    impressions.getGoodBotCount()))
                    .queue();

        } catch (Exception e) {
            log.error(e.getMessage());
            if (!event.isAcknowledged()) {
                event.getHook().editOriginal("Command failed to execute").queue();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GoodBotCommand that = (GoodBotCommand) o;
        return Objects.equals(impressionsService, that.impressionsService) && Objects.equals(victGuildService, that.victGuildService) && Objects.equals(data, that.data) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), impressionsService, victGuildService, data, name, description);
    }
}
