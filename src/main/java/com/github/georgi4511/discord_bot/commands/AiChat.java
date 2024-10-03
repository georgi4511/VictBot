package com.github.georgi4511.discord_bot.commands;

import com.github.georgi4511.discord_bot.models.SlashCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter
@Slf4j
@Component
public class AiChat extends SlashCommand {
    private final OllamaChatModel ollamaChatModel;
    private SlashCommandData data;
    private String name;
    private String description;
    public AiChat(OllamaChatModel ollamaChatModel){
        this.ollamaChatModel = ollamaChatModel;
        this.name = "ai_chat_java";
        this.description = "talk to ollama through java";
        this.data = Commands.slash(this.name,this.description).addOption(OptionType.STRING,"prompt","the prompt sent to the bot",true);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
          event.deferReply().queue();

          String prompt = Objects.requireNonNull(event.getOption("prompt")).getAsString();
            String res = ollamaChatModel.call(prompt);
            res = res.length()>=2000 ? res.substring(0,1997)+"..." : res;
            event.getHook().editOriginal(res).queue();
        } catch (Exception e) {
            log.info(e.getMessage());
            event.reply("Sorry command failed to execute").queue();
        }
    }
}