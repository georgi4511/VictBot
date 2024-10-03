package com.github.georgi4511.discord_bot.eventListeners;
import com.github.georgi4511.discord_bot.commands.CommandRegistry;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class InteractionEventListener extends ListenerAdapter {
    private final JDA jda;
    private final CommandRegistry commandRegistry;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        log.info(event.getName());
                        var command = commandRegistry.getCommandByName(commandName);
                        if(command!=null) {
                            command.callback(event);
                        } else {
                            event.reply("Unknown command").setEphemeral(true).queue();
                        }
    }

        @PostConstruct
    public void registerListeners() {
        jda.addEventListener(this);
    }
}