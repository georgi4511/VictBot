package com.github.georgi4511.discord_bot.commands.random;

import com.github.georgi4511.discord_bot.models.SlashCommand;
import com.github.georgi4511.discord_bot.dtos.CatPictureDto;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Getter
@Setter
@Component
public class CatPic extends SlashCommand {
    public SlashCommandData data;
    private String name;
    private String description;

    public CatPic(){
        this.name = "cat";
        this.description = "receive random cat";
        this.data = Commands.slash(name,description).setGuildOnly(true);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
           try (HttpClient httpClient = HttpClient.newBuilder().build())
           {
               HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().GET().uri(URI.create("https://api.thecatapi.com/v1/images/search")).build(), HttpResponse.BodyHandlers.ofString());
               String body = response.body();
               CatPictureDto[] catPictureDto = new Gson().fromJson(body, CatPictureDto[].class);
               event.reply(catPictureDto[0].getUrl()).queue();
           }
            } catch (IOException | InterruptedException e) {
            event.reply("Sorry command failed to execute").queue();
            throw new RuntimeException(e);
        }
    }
}



