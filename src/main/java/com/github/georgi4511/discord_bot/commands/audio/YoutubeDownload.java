package com.github.georgi4511.discord_bot.commands.audio;

import com.github.georgi4511.discord_bot.models.BaseCommandImpl;
import com.github.georgi4511.discord_bot.models.Video;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

import static com.github.georgi4511.discord_bot.utils.Utils.*;

@Component
@Getter
@Setter
public class YoutubeDownload extends BaseCommandImpl {
    private static final Logger log = LoggerFactory.getLogger(YoutubeDownload.class);
    public static final String PROMPT = "prompt";
    private SlashCommandData data;
    private String name;
    private String description;

    public YoutubeDownload() {
        this.name = "youtube-download";
        this.description = "downloads youtube video from link and returns audio file";
        this.data = Commands.slash(this.name, this.description).addOption(OptionType.STRING, PROMPT, "the prompt sent to youtube", true);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
            event.deferReply().queue();

            String prompt = Objects.requireNonNull(event.getOption(PROMPT)).getAsString();

            String url = searchYoutubeAndReturnLink(prompt);

            Video video =  downloadVideo(url,System.getProperty("user.dir"));

            event.getHook().editOriginal(url).queue();
            event.getHook().editOriginal("download completed").queue();
            assert video != null;
            event.getHook().sendFiles(FileUpload.fromData(new File(video.getFilePath()))).queue();
        } catch (Exception e) {
            e.printStackTrace();
            event.getHook().editOriginal("Sorry command failed to execute").queue();
            Thread.currentThread().interrupt();
        }
    }


}
