package com.github.georgi4511.discord_bot.listeners;
import com.github.georgi4511.discord_bot.handlers.CommandHandler;
import com.github.georgi4511.discord_bot.services.JsonService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class EventListener extends ListenerAdapter {

    private final JDA jda;
    private final CommandHandler commandHandler;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        log.info(commandName);
        var command = commandHandler.getCommandByName(commandName);
        if(command!=null) {
            command.callback(event);
        } else {
            event.reply("Unknown command").setEphemeral(true).queue();
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        String content = event.getMessage().getContentRaw();
        if(content.startsWith("!")) {
            log.info("{}:{}", event.getAuthor().getName(), content);
        }
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!ping")) {
            event.getMessage().reply("Pong!").complete();
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event)
    {
        log.info("{} logged in.", event.getJDA().getSelfUser().getName());
        JsonService.instantiateJsons();
        commandHandler.registerCommands();
    }

    @PostConstruct
    public void registerListeners() {
        jda.addEventListener(this);
    }

}