package com.github.georgi4511.victbot.listener;

import com.github.georgi4511.victbot.model.CommandCooldownRecord;
import com.github.georgi4511.victbot.model.VictCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public interface CommandInteractionHandler {
    void handleSlashCommandExecution(@NotNull SlashCommandInteractionEvent event, User eventUser, VictCommand command);

    void validateCommandExecution(
            ArrayList<CommandCooldownRecord> cooldownRecords, String commandName, Long commandCooldown);

    void validateExistingOrCreateEntities(User eventUser, Guild eventGuild);

    }
