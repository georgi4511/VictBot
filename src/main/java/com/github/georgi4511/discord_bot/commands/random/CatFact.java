package com.github.georgi4511.discord_bot.commands.random;

import com.github.georgi4511.discord_bot.models.SlashCommand;
import com.github.georgi4511.discord_bot.dtos.CatFactDto;
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
public class CatFact extends SlashCommand {
    private SlashCommandData data;
    private String name;
    private String description;
    public CatFact(){
        this.name = "cat_fact";
        this.description = "receive random cat fact 🐈";
        this.data = Commands.slash(name,description).setGuildOnly(true);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
           try (HttpClient httpClient = HttpClient.newBuilder().build())
           {
               HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().GET().uri(URI.create("https://catfact.ninja/fact")).build(), HttpResponse.BodyHandlers.ofString());
               String body = response.body();
               CatFactDto catPictures = new Gson().fromJson(body, CatFactDto.class);
               event.reply(catPictures.getFact()).queue();
           }
        } catch (IOException | InterruptedException e) {
            event.reply("Sorry command failed to execute").queue();
            throw new RuntimeException(e);
        }
    }
}