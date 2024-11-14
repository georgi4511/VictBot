package com.github.georgi4511.discord_bot.utils;

import com.github.georgi4511.discord_bot.models.Video;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {
    public static final String TWITTER_URL = "https://twitter.com/";
    public static final String X_URL = "https://x.com/";
    public static final String FXTWITTER_URL = "https://fxtwitter.com/";

    private Utils() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Cannot initialize helper class");
    }

    public static void fixTwitter(@NotNull MessageReceivedEvent event, @NotNull String content) {
        if(content.startsWith(X_URL)) {
            String fixedContent = String.format("%s sent this:%n%s", event.getAuthor().getEffectiveName(), content.replaceFirst(X_URL, FXTWITTER_URL));
            event.getMessage().delete().queue();
            event.getChannel().sendMessage(fixedContent).queue();
        }
        else if(content.startsWith(TWITTER_URL)) {
            String fixedContent = String.format("%s sent this:%n%s", event.getAuthor().getEffectiveName(), content.replaceFirst(TWITTER_URL, FXTWITTER_URL));
            event.getMessage().delete().queue();
            event.getChannel().sendMessage(fixedContent).queue();
        }
    }
    public static @NotNull String searchYoutubeAndReturnTitles(int amount, String prompt) throws IOException, InterruptedException {
        String videoTitle = "--get-title";
        return youtubeDlp(amount, prompt, videoTitle);
    }
    public static @NotNull String searchYoutubeAndReturnLink(String prompt) throws IOException, InterruptedException {
        String videoId = "--get-id";
        return youtubeDlp(1, prompt, videoId);
    }

    private static @NotNull String youtubeDlp(int amount, String prompt, String videoId) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "yt-dlp", String.format("ytsearch%d:%s",amount, prompt.replaceAll(" ","_")), videoId, "--no-warnings","--quiet");

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        process.waitFor();
        return output.toString();
    }


    public static Video downloadVideo(String videoUrl, String outputDirectory) {
        String testTitle = "test";
        ProcessBuilder processBuilder = new ProcessBuilder("yt-dlp", "-o", outputDirectory + "/%(title)s.%(ext)s", videoUrl,"-f","251");
        processBuilder.redirectErrorStream(true); // Combine error and output streams

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String title = ""; // To store the video title

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("[download] Destination:")) {
                    // Extracting title from the line
                    title = line.split("Destination: ")[1].split("\\.")[0]; // Get title without extension
                }
            }

            process.waitFor(); // Wait for the process to finish

            // Create a Video object with the downloaded file path
            return new Video(title, outputDirectory + "/" + title + ".mp4"); // Assuming mp4 format; adjust as needed
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
