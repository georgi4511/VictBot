package com.github.georgi4511.victbot.command.admin;

import com.github.georgi4511.victbot.entity.BaseCommandImpl;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.service.VictGuildService;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Getter
@Setter
@Component
public class BadBot extends BaseCommandImpl {
    private static final Logger log = LoggerFactory.getLogger(BadBot.class);
    private final VictGuildService victGuildService;
    private SlashCommandData data;
    private String name;
    private String description;

    public BadBot(VictGuildService victGuildService) {
        this.name = "badbot";
        this.description = "When bot is bad";
        this.data = Commands.slash(this.name, this.description);
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

            Integer badBotCount = victGuildService.incrementBadBotCount(victGuild);

            event.reply(String.format("I have received %d bad bot impressions. Frick you.", badBotCount)).queue();

        } catch (Exception e) {
            log.error(e.getMessage());
            event.getHook().sendMessage("Command failed to execute").setEphemeral(true).queue();
        }
    }
}