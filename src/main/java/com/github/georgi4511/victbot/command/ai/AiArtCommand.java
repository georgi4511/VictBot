package com.github.georgi4511.victbot.command.ai;

import com.github.georgi4511.victbot.command.AbstractVictCommand;
import com.github.georgi4511.victbot.model.GenerateImageInput;
import com.github.georgi4511.victbot.service.AiGenerationService;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.utils.AttachedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@RequiredArgsConstructor
public class AiArtCommand extends AbstractVictCommand {
  public static final String PROMPT = "prompt";
  public static final String NEGATIVE_PROMPT = "negative-prompt";
  public static final String SAMPLER_NAME = "sampler-name";
  public static final String STEPS = "steps";
  public static final String CFG_SCALE = "cfg-scale";
  public static final String WIDTH = "width";
  public static final String HEIGHT = "height";
  private static final Logger log = LoggerFactory.getLogger(AiArtCommand.class);

  private final AiGenerationService aiGenerationService;
  private final SlashCommandData data =
      Commands.slash("ai-art", "create ai image using stable diffusion")
          .addOption(OptionType.STRING, PROMPT, "the prompt sent to the bot", true)
          .addOption(OptionType.STRING, NEGATIVE_PROMPT, "the negative prompt sent to the bot")
          .addOption(OptionType.STRING, SAMPLER_NAME, "sampler to use")
          .addOption(OptionType.INTEGER, STEPS, "steps for image generation")
          .addOption(OptionType.INTEGER, CFG_SCALE, "cfg-scale for image generation")
          .addOption(OptionType.INTEGER, WIDTH, "width for image generation")
          .addOption(OptionType.INTEGER, HEIGHT, "height for image generation");

  private Boolean isDeferred = true;

  @Override
  protected void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent) {
    slashCommandInteractionEvent.deferReply().queue();

    GenerateImageInput imageInput = getGenerateImageInput(slashCommandInteractionEvent.getOptions());

    byte[] image = aiGenerationService.generateImage(imageInput);

    slashCommandInteractionEvent.getHook().editOriginalAttachments(AttachedFile.fromData(image, "o.png")).queue();
  }

  private GenerateImageInput getGenerateImageInput(List<OptionMapping> options) {

    String prompt = "";
    String negativePrompt = "";
    String samplerName = "Euler a";
    Integer steps = 28;
    int width = 1024;
    int height = 1024;
    int cfgScale = 5;

    for (OptionMapping option : options) {
      switch (option.getName()) {
        case PROMPT -> prompt = option.getAsString();
        case NEGATIVE_PROMPT -> negativePrompt = option.getAsString();
        case SAMPLER_NAME -> samplerName = option.getAsString();
        case WIDTH -> width = option.getAsInt();
        case HEIGHT -> height = option.getAsInt();
        case CFG_SCALE -> cfgScale = option.getAsInt();
        default -> log.info("Unexpected option {}", option.getName());
      }
    }

    return new GenerateImageInput(
        prompt, negativePrompt, steps, width, height, cfgScale, samplerName);
  }
}
