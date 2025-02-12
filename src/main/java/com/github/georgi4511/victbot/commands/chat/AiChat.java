package com.github.georgi4511.victbot.commands.chat;

import com.github.georgi4511.victbot.models.BaseCommandImpl;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter
@Component
public class AiChat extends BaseCommandImpl {
    private static final Logger log = LoggerFactory.getLogger(AiChat.class);
    private final OllamaChatModel ollamaChatModel;
    private SlashCommandData data;
    private String name;
    private String description;

    public AiChat(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
        this.name = "ai_chat_java";
        this.description = "talk to ollama through java";
        this.data = Commands.slash(this.name, this.description).addOption(OptionType.STRING, "prompt", "the prompt sent to the bot", true);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
            event.deferReply().queue();

            String prompt = Objects.requireNonNull(event.getOption("prompt")).getAsString();
            String res = ollamaChatModel.call(prompt);
            res = res.length() >= 2000 ? res.substring(0, 1997) + "..." : res;
            event.getHook().editOriginal(res).queue();
        } catch (Exception e) {
            log.info(e.getMessage());
            event.reply("Sorry command failed to execute").queue();
        }
    }
}