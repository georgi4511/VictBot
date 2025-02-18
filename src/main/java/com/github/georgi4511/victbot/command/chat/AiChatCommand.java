/* (C)2025 */
package com.github.georgi4511.victbot.command.chat;

import com.github.georgi4511.victbot.entity.VictCommand;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AiChatCommand extends VictCommand {
  private static final Logger log = LoggerFactory.getLogger(AiChatCommand.class);
  private final OllamaChatModel ollamaChatModel;
  private SlashCommandData data;
  private String name;
  private String description;

  public AiChatCommand(OllamaChatModel ollamaChatModel) {
    this.ollamaChatModel = ollamaChatModel;
    this.name = "ai-chat";
    this.description = "talk to ollama through java";
    this.data =
        Commands.slash(this.name, this.description)
            .addOption(OptionType.STRING, "prompt", "the prompt sent to the bot", true);
  }

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    event.deferReply().queue();

    try {
      String prompt = Objects.requireNonNull(event.getOption("prompt")).getAsString();
      UserMessage message = new UserMessage(prompt);
      String res = ollamaChatModel.call(message);
      res = res.length() >= 2000 ? res.substring(0, 1997) + "..." : res;
      event.getHook().editOriginal(res).queue();
    } catch (Exception e) {
      log.info(e.getMessage());
      event.getHook().editOriginal("Sorry command failed to execute").queue();
    }
  }
}
