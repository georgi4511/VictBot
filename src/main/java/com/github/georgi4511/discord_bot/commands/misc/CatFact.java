package com.github.georgi4511.discord_bot.commands.misc;

import com.github.georgi4511.discord_bot.models.BaseCommandImpl;
import com.github.georgi4511.discord_bot.services.CatFactService;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CatFact extends BaseCommandImpl {
    private static final Logger log = LoggerFactory.getLogger(CatFact.class);
    private final SlashCommandData data;
    private final String name;
    private final String description;
    private final CatFactService catFactService;

    @Autowired
    public CatFact(CatFactService catFactService){
        this.name = "cat_fact";
        this.description = "receive random cat fact 🐈";
        this.data = Commands.slash(this.name,this.description);
        this.catFactService = catFactService;
    }

    @Override
    public void callback(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        try {
            String fact = catFactService.getRandomCatFact();
            event.getHook().sendMessage(fact).queue();
        } catch (Exception e) {
            event.getHook().sendMessage("Sorry, I couldn't fetch a cat fact right now.").queue();
            log.error(e.getMessage());
        }
    }
}