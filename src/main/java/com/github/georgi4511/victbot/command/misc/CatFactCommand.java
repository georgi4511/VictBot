package com.github.georgi4511.victbot.command.misc;

import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.CatFactService;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter
@Component
public class CatFactCommand extends VictCommand {
    private static final Logger log = LoggerFactory.getLogger(CatFactCommand.class);
    private final SlashCommandData data;
    private final String name;
    private final String description;
    private final CatFactService catFactService;

    public CatFactCommand(CatFactService catFactService) {
        this.name = "cat-fact";
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CatFactCommand that = (CatFactCommand) o;
        return Objects.equals(data, that.data)
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(catFactService, that.catFactService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data, name, description, catFactService);
    }
}
