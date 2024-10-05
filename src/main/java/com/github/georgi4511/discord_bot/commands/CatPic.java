package com.github.georgi4511.discord_bot.commands;

import com.github.georgi4511.discord_bot.dtos.CatPictureDto;
import com.github.georgi4511.discord_bot.models.VictBaseCommand;
import com.github.georgi4511.discord_bot.services.CatFactService;
import com.github.georgi4511.discord_bot.services.CatPicService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Component
@Slf4j
public class CatPic extends VictBaseCommand {
    private final SlashCommandData data;
    private final String name;
    private final String description;
    private final CatPicService catPicService;

    @Autowired
    public CatPic(CatPicService catPicService){
        this.name = "cat";
        this.description = "receive random cat";
        this.data = Commands.slash(this.name,this.description);
        this.catPicService = catPicService;
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        try {
            String catPicture = catPicService.getRandomCatPicture();
            event.getHook().sendMessage(catPicture).queue();
        } catch (Exception e) {
            event.getHook().sendMessage("Sorry, I couldn't fetch a cat picture right now.").queue();
            log.error(e.getMessage());
        }
    }
}



