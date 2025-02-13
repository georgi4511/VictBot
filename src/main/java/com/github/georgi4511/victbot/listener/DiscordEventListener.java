package com.github.georgi4511.victbot.listener;

import com.github.georgi4511.victbot.entity.BaseCommandImpl;
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

@Component
public class DiscordEventListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(DiscordEventListener.class);
    private final Map<String, BaseCommandImpl> commands;

    DiscordEventListener(List<BaseCommandImpl> commandList) {
        this.commands = new HashMap<>();
        commandList.forEach(command -> commands.put(command.getName(), command)
        );
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        BaseCommandImpl command = commands.get(commandName);
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
        BaseCommandImpl command = commands.get(customId.split("_", 2)[0]);
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