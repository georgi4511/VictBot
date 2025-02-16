package com.github.georgi4511.victbot.command.misc;

import com.github.georgi4511.victbot.entity.VictCommand;
import com.github.georgi4511.victbot.service.CatPicService;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CatPic extends VictCommand {
    private static final Logger log = LoggerFactory.getLogger(CatPic.class);
    private final SlashCommandData data;
    private final String name;
    private final String description;
    private final CatPicService catPicService;

    public CatPic(CatPicService catPicService) {
        this.name = "cat";
        this.description = "receive random cat";
        this.data = Commands.slash(this.name, this.description);
        this.catPicService = catPicService;
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String catPicture = catPicService.getRandomCatPicture();
        event.getHook().sendMessage(catPicture).queue();
    }
}



