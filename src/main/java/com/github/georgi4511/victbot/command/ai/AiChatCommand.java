package com.github.georgi4511.victbot.command.ai;

import com.github.georgi4511.victbot.model.AbstractVictCommand;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@RequiredArgsConstructor
public class AiChatCommand extends AbstractVictCommand {
  private static final Logger log = LoggerFactory.getLogger(AiChatCommand.class);
  private final OllamaChatModel ollamaChatModel;
  private SlashCommandData data =
      Commands.slash("ai-chat", "talk to ollama through java")
          .addOption(OptionType.STRING, "prompt", "the prompt sent to the bot", true);

  private Boolean isDeferred = true;

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    event.deferReply().queue();

    String prompt = Objects.requireNonNull(event.getOption("prompt")).getAsString();
    UserMessage message = new UserMessage(prompt);
    String res = ollamaChatModel.call(message);

    if (res.length() <= 2000) {
      event.getHook().editOriginal(res).queue();
      return;
    }

    List<String> strings = List.of(res.split("", 2000));

    for (String string : strings) {
      event.getHook().sendMessage(string).queue();
    }
    event.getHook().deleteOriginal().queue();
  }
}
