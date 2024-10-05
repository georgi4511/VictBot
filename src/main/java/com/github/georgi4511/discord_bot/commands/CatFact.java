package com.github.georgi4511.discord_bot.commands;

import com.github.georgi4511.discord_bot.commands.personal.GetWaifu;
import com.github.georgi4511.discord_bot.models.SlashCommand;
import com.github.georgi4511.discord_bot.dtos.CatFactDto;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
        this.data = Commands.slash(this.name,this.description);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        RestTemplate restTemplate = new RestTemplate();
        CatFactDto catFactDto = restTemplate
                .getForObject("https://catfact.ninja/fact", CatFactDto.class);
        assert catFactDto != null;
        event.reply(catFactDto.getFact()).queue();
    }
}