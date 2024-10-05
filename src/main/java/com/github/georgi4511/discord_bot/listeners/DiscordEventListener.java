package com.github.georgi4511.discord_bot.listeners;
import com.github.georgi4511.discord_bot.models.VictBaseCommand;
import com.github.georgi4511.discord_bot.services.JsonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.georgi4511.discord_bot.utils.Utils.fixTwitter;

@Slf4j
@Component
public class DiscordEventListener extends ListenerAdapter {

    private final Map<String, VictBaseCommand> commands;

    DiscordEventListener(List<VictBaseCommand> commandList){
        this.commands = new HashMap<>();
        commandList.forEach(command-> commands.put(command.getName(),command)
        );
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        VictBaseCommand command = commands.get(commandName);
        if(command!=null) {
            command.callback(event);
        } else {
            event.reply("Unknown command").setEphemeral(true).queue();
        }
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event){
        String customId = event.getComponentId();

        // Assuming custom IDs are in the format "commandName_menuIdentifier"
        VictBaseCommand command = commands.get(customId.split("_",2)[0]);
        if(command!=null){
            command.handleSelectInteraction(event);
        }
        else{
            event.reply("Unknown select interaction").setEphemeral(true).queue();
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        if(event.getAuthor().isBot()) return;
        String content = event.getMessage().getContentRaw();
        fixTwitter(event,content);
    }



    @Override
    public void onReady(@NotNull ReadyEvent event)
    {
        log.info("{} logged in.", event.getJDA().getSelfUser().getEffectiveName());
        JsonService.instantiateJsons();
    }

}