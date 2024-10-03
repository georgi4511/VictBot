package com.github.georgi4511.discord_bot.commands.random;

import com.github.georgi4511.discord_bot.commands.ClientInteraction;
import com.github.georgi4511.discord_bot.mappers.CatFactResponse;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Getter
@Setter
public class CatFact extends ClientInteraction {

    public SlashCommandData data;


    public CatFact(){
        this.data = Commands.slash("cat_fact","receive random cat fact 🐈").setGuildOnly(true);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        try {
           try (HttpClient httpClient = HttpClient.newBuilder().build())
           {
               HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().GET().uri(URI.create("https://catfact.ninja/fact")).build(), HttpResponse.BodyHandlers.ofString());
               String body = response.body();
               CatFactResponse catPictures = new Gson().fromJson(body, CatFactResponse.class);
               event.reply(catPictures.getFact()).queue();
           }
        } catch (IOException | InterruptedException e) {
            event.reply("Sorry command failed to execute").queue();
            throw new RuntimeException(e);
        }
    }
}