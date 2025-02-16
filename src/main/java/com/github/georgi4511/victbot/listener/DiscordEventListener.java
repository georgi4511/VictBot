package com.github.georgi4511.victbot.listener;

import com.github.georgi4511.victbot.entity.VictCommand;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.entity.VictUser;
import com.github.georgi4511.victbot.service.VictGuildService;
import com.github.georgi4511.victbot.service.VictUserService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.georgi4511.victbot.util.Utils.fixTwitter;
import static java.util.Objects.isNull;

@Component
public class DiscordEventListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(DiscordEventListener.class);
    private final Map<String, VictCommand> commands;
    private final VictGuildService victGuildService;
    private final VictUserService victUserService;

    DiscordEventListener(List<VictCommand> commandList, VictUserService victUserService, VictGuildService victGuildService) {
        this.commands = new HashMap<>();
        commandList.forEach(command -> commands.put(command.getName(), command));

        this.victUserService = victUserService;
        this.victGuildService = victGuildService;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        VictCommand command = commands.get(commandName);

        if (!victUserService.existsVictUserByDiscordId(event.getUser().getId()))
            victUserService.saveVictUser(new VictUser(event.getUser().getId()));
        if (!isNull(event.getGuild()) && !victGuildService.existsVictGuildByDiscordId(event.getGuild().getId())) {
            victGuildService.saveVictGuild(new VictGuild(event.getGuild().getId()));
        }

        if (command != null) {
            command.callback(event);
        } else {
            event.reply("Unknown command").setEphemeral(true).queue();
        }
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        String customId = event.getComponentId();

        // Assuming custom IDs are in the format "commandName_menuIdentifier"
        VictCommand command = commands.get(customId.split("_", 2)[0]);
        if (command != null) {
            command.handleSelectInteraction(event);
        } else {
            event.reply("Unknown select interaction").setEphemeral(true).queue();
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String content = event.getMessage().getContentRaw();
        fixTwitter(event, content);
    }


    @Override
    public void onReady(@NotNull ReadyEvent event) {
        log.info("{} logged in.", event.getJDA().getSelfUser().getEffectiveName());
    }

}