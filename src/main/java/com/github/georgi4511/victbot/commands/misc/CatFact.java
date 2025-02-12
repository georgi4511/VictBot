package com.github.georgi4511.victbot.commands.misc;

import com.github.georgi4511.victbot.models.BaseCommandImpl;
import com.github.georgi4511.victbot.services.CatFactService;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public CatFact(CatFactService catFactService) {
        this.name = "cat_fact";
        this.description = "receive random cat fact 🐈";
        this.data = Commands.slash(this.name, this.description);
        this.catFactService = catFactService;
    }

    @Override
    public void callback(@NotNull SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String fact = catFactService.getRandomCatFact();
        event.getHook().sendMessage(fact).queue();
    }
}