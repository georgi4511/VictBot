package com.github.georgi4511.discord_bot.commands.audio;

import com.github.georgi4511.discord_bot.commands.chat.AiChat;
import com.github.georgi4511.discord_bot.models.BaseCommandImpl;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static com.github.georgi4511.discord_bot.utils.Utils.searchYoutubeAndReturnTitles;

@Component
@Getter
@Setter
public class YoutubeSearch extends BaseCommandImpl {
    private static final Logger log = LoggerFactory.getLogger(YoutubeSearch.class);
    public static final String PROMPT = "prompt";
    public static final String AMOUNT = "amount";
    private SlashCommandData data;
    private String name;
    private String description;

    public YoutubeSearch() {
        this.name = "youtube-search";
        this.description = "serves for 1<=N<=10 amount of youtube videos by a query and returns top titles";
        this.data = Commands.slash(this.name, this.description).addOption(OptionType.STRING, PROMPT, "the prompt sent to youtube", true).addOption(OptionType.INTEGER, AMOUNT, "the amount of results", true);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
            event.deferReply().queue();

            String prompt = Objects.requireNonNull(event.getOption(PROMPT)).getAsString();
            int amount = Objects.requireNonNull(event.getOption(AMOUNT)).getAsInt();

            String output = searchYoutubeAndReturnTitles(amount, prompt);


            event.getHook().editOriginal(output).queue();
        } catch (Exception e) {
            e.printStackTrace();
            event.getHook().editOriginal("Sorry command failed to execute").queue();
            Thread.currentThread().interrupt();
        }
    }


}
