package com.github.georgi4511.discord_bot.commands;

import com.github.georgi4511.discord_bot.dtos.CatFactDto;
import com.github.georgi4511.discord_bot.models.SlashCommand;
import com.github.georgi4511.discord_bot.dtos.CatPictureDto;
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
import java.util.List;
import java.util.Objects;

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
        this.data = Commands.slash(this.name,this.description);
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        RestTemplate restTemplate = new RestTemplate();
        List<CatPictureDto> catPictureDto = List.of(Objects.requireNonNull(restTemplate
                .getForObject("https://api.thecatapi.com/v1/images/search", CatPictureDto[].class)));
               event.reply(catPictureDto.getFirst().getUrl()).queue();
    }
}



