package com.github.georgi4511.discord_bot.listeners;
import com.github.georgi4511.discord_bot.handlers.CommandHandler;
import com.github.georgi4511.discord_bot.models.SlashCommandStringSelectMenu;
import com.github.georgi4511.discord_bot.services.JsonService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import static com.github.georgi4511.discord_bot.utils.Utils.fixTwitter;

@Slf4j
@AllArgsConstructor
@Component
public class EventListener extends ListenerAdapter {

    private final JDA jda;
    private final CommandHandler commandHandler;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        var command = commandHandler.getCommandByName(commandName);
        if(command!=null) {
            command.callback(event);
        } else {
            event.reply("Unknown command").setEphemeral(true).queue();
        }
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event){
        String customId = event.getComponentId();

        SlashCommandStringSelectMenu command = commandHandler.getSlashCommandStringSelectMenuByCustomId(customId);
        if(command!=null){
            command.selectMenuCallback(event);
        }
        else{
            event.reply("Issue occurred").setEphemeral(true).queue();
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
        commandHandler.registerCommands();
    }

    @PostConstruct
    public void registerListeners() {
        jda.addEventListener(this);
    }

}